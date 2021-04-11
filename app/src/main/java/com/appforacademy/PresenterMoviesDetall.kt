package com.appforacademy

import android.view.View
import com.android.academy.fundamentals.homework.features.data.Movie

class PresenterMoviesDetall(private val movieData: Movie?) {


    private var viewMoviesDetall:ViewMoviesDetall?=null

   // private var movieData: Movie?=null

    fun addInfoByMovieDetallFromPresenter(){
        viewMoviesDetall?.addInfoMovieDetal(movieData)
    }
    fun openActorAdapterFromPresenter(){
        viewMoviesDetall?.openActorAdapter(movieData)
    }



    fun attachView(view: ViewMoviesDetall) {
        this.viewMoviesDetall = view
    }
    fun detachView() {
        this.viewMoviesDetall = null
    }


}