package com.appforacademy.serviseAndMeneger

import android.app.Service
import android.content.ContentValues
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.appforacademy.PresenterMoviesList
import com.appforacademy.data.APIPlaynow
import com.appforacademy.data.ApiMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun updateInfoViaServise(){
        Log.i(ContentValues.TAG, "Запущеный сервис!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

        CoroutineScope(Dispatchers.IO).launch {
            var firstWelcomeMovies:APIPlaynow = RetrofitModule.MoviesApi.getMovie()

            var newListMovies = presenterForUpdate.creatNewListMovies(firstWelcomeMovies)
            //сохранение в локальную базу данных
            presenterForUpdate.insertMoviesInLocalDB(newListMovies)
            Log.i(ContentValues.TAG, "Сохранилось в БД!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

        }
        /*

            var firstWelcomeMovies:APIPlaynow = RetrofitModule.MoviesApi.getMovie()
           //

            viewMoviesList?.loadMoviesInListFromOnline(newListMovies)
            //сохранение в локальную базу данных
            insertMoviesInLocalDB(newListMovies)*/
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