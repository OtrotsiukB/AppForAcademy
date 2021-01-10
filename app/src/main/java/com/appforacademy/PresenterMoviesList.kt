package com.appforacademy

import com.android.academy.fundamentals.homework.features.data.Movie

class PresenterMoviesList {

    private var viewMoviesList:ViewMoviesList?=null

    fun loadMoviesInListfromPresenter(){
        viewMoviesList?.loadMoviesInList()
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

}