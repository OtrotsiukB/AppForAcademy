package com.appforacademy.serviseAndMeneger

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.ContentValues
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.ext.KotlinTypeNames.CHANNEL
import com.android.academy.fundamentals.homework.features.data.Movie
import com.appforacademy.MainActivity
import com.appforacademy.PresenterMoviesList
import com.appforacademy.R
import com.appforacademy.data.APIPlaynow
import com.appforacademy.data.ApiMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.metadata.internal.metadata.jvm.JvmProtoBuf.flags
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

class UpdateBoundService: Service() {
     val binder = UpdateBoundServiceBinder()

    val presenterForUpdate=PresenterMoviesList()
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    suspend fun showNotification(id: String,name:String,rating:String){
        var intent = Intent(applicationContext, MainActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .putExtra("movieId", id)


        val pendingIntent =  PendingIntent.getActivity(applicationContext, 1, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)



        val notification = NotificationCompat.Builder(applicationContext, "AAMovies")
            .setContentTitle("Андроид Академия Фильмы")
            .setContentText("Лучший фильм: "+name+" рейтинг:"+rating)
            .setSmallIcon(R.drawable.ic_message)
            .setContentIntent(pendingIntent)

        //.setSmallIcon(R.drawable.ic_message)
        // .setWhen(message.timestamp)
        // .setLargeIcon(bitmapIcon)
        // .build()
        Log.i(ContentValues.TAG, "создалось notification!")

        try {


            with(NotificationManagerCompat.from(applicationContext)) {
                // notificationId is a unique int for each notification that you must define
                notify(2, notification.build())
            }
        }  catch (e: Exception) {
            Log.i(ContentValues.TAG, "error"+e.toString())
        }
    }
    fun updateInfoViaServise(){
        Log.i(ContentValues.TAG, "Запущеный сервис!")

        CoroutineScope(Dispatchers.IO).launch {
            var firstWelcomeMovies:APIPlaynow = RetrofitModule.MoviesApi.getMovie()

            var newListMovies = presenterForUpdate.creatNewListMovies(firstWelcomeMovies)
            //сохранение в локальную базу данных
            presenterForUpdate.insertMoviesInLocalDB(newListMovies)
            Log.i(ContentValues.TAG, "Сохранилось в БД!")

          // var topic = newListMovies.sortedBy(Movie::ratings) //{ it.ratings.first }

            var listmovies= newListMovies.toMutableList()
            listmovies.sortBy { it.ratings }


            var beteeMovie = listmovies.size-1
            //показать уведомление с фильмом
            showNotification(listmovies[beteeMovie].id.toString(),listmovies[beteeMovie].title.toString(),listmovies[beteeMovie].ratings.toString())

        }

    }

    inner class UpdateBoundServiceBinder : Binder() {
        fun getService(): UpdateBoundService {
            return this@UpdateBoundService
        }
    }

    private interface MovieApi {
        //https://api.themoviedb.org/3/movie/now_playing?api_key=af310d8d095ef00f027a608e81a1be5e
        @GET("3/movie/now_playing?api_key=af310d8d095ef00f027a608e81a1be5e")
        suspend fun getMovie(): APIPlaynow

        @GET("/3/movie/{id}?api_key=af310d8d095ef00f027a608e81a1be5e&language=en-US")
        suspend fun getMovieOnly(@Path("id") id: String ): ApiMovie
    }
    private object RetrofitModule {
        private var baseUrl:String="https://api.themoviedb.org/"

        private var contentType = "application/json".toMediaType()


        private val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            coerceInputValues = true
        }

        private val httpClient = OkHttpClient.Builder().build()
        val apiKey = "af310d8d095ef00f027a608e81a1be5e"
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)

            .addConverterFactory(GsonConverterFactory.create())

            .build()


        val MoviesApi: MovieApi = retrofit.create()
    }


}