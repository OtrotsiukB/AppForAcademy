package com.appforacademy

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras

import androidx.room.Room
import com.android.academy.fundamentals.homework.features.data.Movie
import com.appforacademy.DBRoom.DatabaseR
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

import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController

import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions

//import androidx.navigation.fragment



class MainActivity : AppCompatActivity(),fragment_movies_list.ClickListener,fragment_movies_details.ClickListenerDetall {

    //private var fragment_movies_detal = fragment_movies_details().apply { setListener(this@MainActivity) }
    private var fragment_movies_list = fragment_movies_list().apply { setListener(this@MainActivity) }


    private fun createNotificationChannel() {
      /*  val channel = NotificationChannelCompat
            .Builder(NEW_MESSAGES, IMPORTANCE_HIGH)
            .setName(getString(R.string.channel_new_messages))
            .setDescription(getString(R.string.channel_new_messages_description))
            .build()
        notificationManagerCompat.createNotificationChannel(channel)
*/
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "AAMovies"
            val descriptionText = "AAMovies description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("AAMovies", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()
      /*  var idFromIntent:String?= intent?.getStringExtra("movieId")
        if (idFromIntent!=null){
            Log.i(ContentValues.TAG, "ИД фильма"+idFromIntent)
            T
        }*/


                 if(savedInstanceState==null) {
          /*  supportFragmentManager.beginTransaction().apply {

                add(R.id.Fragment_container_Main, fragment_movies_list)

                commit()
                Toast.makeText(applicationContext,"ИД фильма",Toast.LENGTH_SHORT)
                intent?.let(::handleIntent)
            }*/
        }

    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }
    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            // Invoked when a dynamic shortcut is clicked.
            Intent.ACTION_VIEW -> {
                var idFromIntent:String?= intent?.getStringExtra("movieId")
                if (idFromIntent!=null){

                    CoroutineScope(Dispatchers.IO).launch {


                        val presenterMoviesList = PresenterMoviesList()

                        var tempApiMovie = RetrofitModule.MoviesApi.getMovieOnly(idFromIntent.toString())
                        var tempMovie = presenterMoviesList.convectorFromApiMovieToMovie(tempApiMovie)

                        ////


                                CoroutineScope(Dispatchers.Main).launch {
                                    openMovieDetall(tempMovie)
                                }

                    }
                }
            }
            // Invoked when a text is shared through Direct Share.

        }
    }

    override fun openMovieDetallTransitions( data: Movie) {
      /*  val emailCardDetailTransitionName = getString(R.string.email_card_detail_transition_name)
     //  /*/ val extras = FragmentNavigatorExtras(cardView to emailCardDetailTransitionName)*/
      //  val extras = FragmentNavigatoeExtras(cardView to emailCardDetailTransitionName )
        // val directions = supportFragmentManager.beginTransaction().apply {
         supportFragmentManager.beginTransaction().apply {
           // .addSharedElement(view, "email_card_detail")
        //    addSharedElement(view, "email_card_detail")
            addToBackStack(null)
            add(R.id.Fragment_container_Main,fragment_movies_details.newInstance(data).apply { setListener(this@MainActivity) })
            commit()

        }

        findNavController().navigate(directions, extras)*/
      /*  val emailCardDetailTransitionName = getString(R.string.email_card_detail_transition_name)
        val extras = FragmentNavigatorExtras(view to emailCardDetailTransitionName)
        val directions = fragment  // Fra.actionHomeFragmentToEmailFragment(email.id)
        findNavController().navigate(directions, extras)*/

       /* val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController*/


        var controler = this.findNavController(R.id.nav_host_fragment)

      //  controler.navigate(R.id.movieListFragment_to_movieFragmentDetall)
        var bundle = bundleOf("movie" to data)
      //  controler.navigate(R.id.movieListFragment_to_movieFragmentDetall,bundle)

//  MovieListFragmentDirections.actionListToDetail(movieId)
       // val movieCardTransactionName = getString(R.string.movie_card_detail_transition_name)
       // val extras = FragmentNavigatorExtras(view to movieCardTransactionName)


       // val directions = MovieListFragmentDirections.movieListFragment_to_movieFragmentDetall(data)

//val di = directions
       // controler.navigate(movi)





      //      MovieListFragmentDirections.actionListToDetail(movieId)
      //  findNavController().navigate(directions, extras)


    }

    override fun openMovieDetall(data: Movie){

        var controler = this.findNavController(R.id.nav_host_fragment)

        //  controler.navigate(R.id.movieListFragment_to_movieFragmentDetall)
        var bundle = bundleOf("movie" to data)
        controler.navigate(R.id.movieListFragment_to_movieFragmentDetall,bundle)

        /*supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            add(R.id.Fragment_container_Main,fragment_movies_details.newInstance(data).apply { setListener(this@MainActivity) })
            commit()

        }*/
     }



//////////////
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
    /////////////
}