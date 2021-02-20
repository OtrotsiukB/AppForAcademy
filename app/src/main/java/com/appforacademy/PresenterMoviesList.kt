package com.appforacademy

import com.android.academy.fundamentals.homework.features.data.Actor
import com.android.academy.fundamentals.homework.features.data.Genre
import com.android.academy.fundamentals.homework.features.data.Movie
import com.appforacademy.data.APIPlaynow
import com.appforacademy.data.ApiMovie

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.*
import kotlinx.serialization.*


import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Path


class PresenterMoviesList {

    private var viewMoviesList:ViewMoviesList?=null



    fun loadMoviesInListfromPresenter(){
        viewMoviesList?.loadMoviesInList()
        CoroutineScope(Dispatchers.IO).launch {
            var firstWelcomeMovies:APIPlaynow = RetrofitModule.MoviesApi.getMovie()
           // var x = firstWelcomeMovies.results
           var newListMovies = creatNewListMovies(firstWelcomeMovies)
            viewMoviesList?.loadMoviesInListFromOnline(newListMovies)
        }

    }

    suspend fun creatNewListMovies(apiPlaynow:APIPlaynow):List<Movie>{
        var newListMovies:MutableList<Movie>?=mutableListOf<Movie>()
        var listResult = apiPlaynow.results
        if (listResult != null) {
            //добавление в список фильмов

            for(x in listResult){
               var tempApiMovie = RetrofitModule.MoviesApi.getMovieOnly(x.id.toString())
                var tempMovie = convectorFromApiMovieToMovie(tempApiMovie)
                newListMovies?.add(tempMovie)
            }

        }

        return newListMovies!!.toList()
    }

    suspend fun convectorFromApiMovieToMovie(tempApiMovie:ApiMovie):Movie{
        var tempGenre: MutableList<Genre>?=  mutableListOf<Genre>()
        var tempApiGenre = tempApiMovie.genres
        if (tempApiGenre != null) {
            for (genre in tempApiGenre){
                var tempGenre1:Genre?= Genre(genre.id!!.toInt(),genre.name.toString())

                if (tempGenre1 != null) {
                    tempGenre?.add(tempGenre1)
                }
            }

        }
        var tempActor: MutableList<Actor>?= mutableListOf<Actor>()
        var tempApiActor = tempApiMovie.production_companies
        if (tempApiActor != null) {
            for(actor in tempApiActor){
                var tempActor1:Actor?= Actor(actor.id!!.toInt(),actor.name.toString(),
                    "https://image.tmdb.org/t/p/w500"+actor.logo_path)

                if (tempActor1 != null) {
                    tempActor?.add(tempActor1)
                }
            }

        }

        var tempMovie:Movie= Movie(tempApiMovie.id!!.toInt(),
            tempApiMovie.original_title.toString(),
            tempApiMovie.overview.toString(),
            "https://image.tmdb.org/t/p/w500"+tempApiMovie.poster_path,
            "https://image.tmdb.org/t/p/w500"+tempApiMovie.backdrop_path,
            tempApiMovie.vote_average!!.toFloat()/2,
            tempApiMovie.vote_count!!.toInt(),
        18,
            tempApiMovie.runtime!!.toInt(),
            tempGenre!!.toList(),tempActor!!.toList())

            return tempMovie
    }

    fun  openMoviesDetallNew(data:Movie){
        viewMoviesList?.openMoviesDetallNew(data)
    }
    fun attachView(view: ViewMoviesList) {
        this.viewMoviesList = view
    }
    fun detachView() {
        this.viewMoviesList = null
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
               // .addConverterFactory(json.asConverterFactory(contentType))
                .addConverterFactory(GsonConverterFactory.create())

                /*.client(
                        OkHttpClient.Builder()
                                .addInterceptor { chain ->
                                    val newHttpUrl = chain.request().url.newBuilder()
                                            .addQueryParameter("api_key", apiKey)
                                            .build()
                                    val newRequest = chain.request().newBuilder()
                                            .url(newHttpUrl)
                                            .build()
                                    chain.proceed(newRequest)
                                }
                                .build()
                )*/
                .build()


        val MoviesApi: MovieApi = retrofit.create()
    }



}