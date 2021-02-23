package com.appforacademy

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Room
import com.android.academy.fundamentals.homework.features.data.Actor
import com.android.academy.fundamentals.homework.features.data.Genre
import com.android.academy.fundamentals.homework.features.data.Movie
import com.appforacademy.DBRoom.DatabaseR
import com.appforacademy.data.*

import kotlinx.coroutines.*


import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.http.Path


class PresenterMoviesList {

    private var viewMoviesList:ViewMoviesList?=null
    private var locationsDb:DatabaseR? = null


    fun loadMoviesInListfromPresenter(){
       // viewMoviesList?.loadMoviesInList()
        CoroutineScope(Dispatchers.IO).launch {
            var listMovieFromDB = getListMoviesFromLocalDB()
            if (listMovieFromDB.size>1){
                viewMoviesList?.loadMoviesInListFromOnline(listMovieFromDB)
            }

            var firstWelcomeMovies:APIPlaynow = RetrofitModule.MoviesApi.getMovie()
           // var x = firstWelcomeMovies.results
           var newListMovies = creatNewListMovies(firstWelcomeMovies)
            viewMoviesList?.loadMoviesInListFromOnline(newListMovies)
            //сохранение в локальную базу данных
            insertMoviesInLocalDB(newListMovies)
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

        var tempMovie:Movie= Movie(
            tempApiMovie.id!!.toInt(),
            tempApiMovie.original_title.toString(),
            tempApiMovie.overview.toString(),
            "https://image.tmdb.org/t/p/w500" + tempApiMovie.poster_path,
            "https://image.tmdb.org/t/p/w500" + tempApiMovie.backdrop_path,
            tempApiMovie.vote_average!!.toFloat() / 2,
            tempApiMovie.vote_count!!.toInt(),
            18,
            tempApiMovie.runtime!!.toInt(),
            tempGenre!!.toList(),
            tempActor!!.toList()
        )

            return tempMovie
    }


    suspend fun insertMoviesInLocalDB(listMovie:List<Movie>){
        locationsDb?.DaoInDB()?.deleteAllMovies()
        locationsDb?.DaoInDB()?.deleteAllActor()
        locationsDb?.DaoInDB()?.deleteAllGenre()
        for (movie in listMovie){
            locationsDb?.DaoInDB()?.insertMovies(convertMovieToMoviePersistensy(movie))
            for (genre in movie.genres){
                locationsDb?.DaoInDB()?.insertGenre(convertGenreToGenrePersistensy(genre,movie.id))
            }
            for (actor in movie.actors){
                locationsDb?.DaoInDB()?.insertActor(convertActorToActorPersistensy(actor,movie.id))
            }
        }

    }

    suspend fun convertActorToActorPersistensy(actor: Actor,idMovie: Int):ActorPersistensy{
        val acterPersistensy:ActorPersistensy= ActorPersistensy(
            null,
            actor.id,
            actor.name,
            actor.picture,
            idMovie
        )
        return acterPersistensy
    }
    suspend fun convertGenreToGenrePersistensy(genre:Genre,idMovie:Int):GenrePersistensy{
        val genrePersistensy:GenrePersistensy= GenrePersistensy(
            null,
            genre.id,
            genre.name,
            idMovie
             )
        return genrePersistensy
    }
    suspend fun convertMovieToMoviePersistensy(movie:Movie):MoviePersistensy{
        val moviePersistensy:MoviePersistensy= MoviePersistensy(
            movie.id,
            movie.title,
            movie.overview,
            movie.poster,
            movie.backdrop,
            movie.ratings,
            movie.numberOfRatings,
            movie.minimumAge,
            movie.runtime)
        return moviePersistensy
    }

    suspend fun getListMoviesFromLocalDB():List<Movie>{
        var newListMovie= mutableListOf<Movie>()

        var movieFromDB = locationsDb?.DaoInDB()?.getAllMovies()
        if (movieFromDB != null) {
            for (moviePersistensy in movieFromDB){
                var newlistGenre= mutableListOf<Genre>()///////////
                var newActor = mutableListOf<Actor>()
                //создаем список жанров
                var listGenrePersistensy=locationsDb?.DaoInDB()?.getGenreFromMovie(moviePersistensy.id.toInt())
                if (listGenrePersistensy != null) {
                    for (genre in listGenrePersistensy)
                   newlistGenre.add(convertGenrePersistensyToGenre(genre))

                }

                //создаем список актеров
                var listActorPersistensy=locationsDb?.DaoInDB()?.getActorFromMovie(moviePersistensy.id.toInt())
                if (listActorPersistensy != null) {
                    for (actor in listActorPersistensy){

                        newActor.add(convertActorPersistensyToActor(actor))
                    }

                }

                //Создание фильма и добавление
                newListMovie.add(makeMovieFromMoviePersistensyAndGenreAndActor(moviePersistensy,newlistGenre.toList(),newActor.toList()))

            }

        }

        return newListMovie

    }
    suspend fun makeMovieFromMoviePersistensyAndGenreAndActor(moviePersistensy: MoviePersistensy,listGenre:List<Genre>,listActor:List<Actor>):Movie{
        var newMovie=Movie(
            moviePersistensy.id,
            moviePersistensy.title,
            moviePersistensy.overview,
            moviePersistensy.poster,
            moviePersistensy.backdrop,
            moviePersistensy.ratings,
            moviePersistensy.numberOfRatings,
            moviePersistensy.minimumAge,
            moviePersistensy.runtime,
            listGenre,
            listActor
        )
        return newMovie
    }
    suspend fun convertActorPersistensyToActor(actorPersistensy: ActorPersistensy):Actor{
        var newActor:Actor= Actor(actorPersistensy.id,actorPersistensy.name,actorPersistensy.picture)
        return newActor
    }
    suspend fun convertGenrePersistensyToGenre(genrePersistensy: GenrePersistensy):Genre{
        var newGenre:Genre=Genre(genrePersistensy.id,genrePersistensy.name)
        return newGenre
    }



    fun  openMoviesDetallNew(data:Movie){
        viewMoviesList?.openMoviesDetallNew(data)

    }
    fun attachView(view: ViewMoviesList) {
        this.viewMoviesList = view
        locationsDb = DatabaseR.create(view.giveContext())//база данных



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

                .addConverterFactory(GsonConverterFactory.create())

                .build()


        val MoviesApi: MovieApi = retrofit.create()
    }



}