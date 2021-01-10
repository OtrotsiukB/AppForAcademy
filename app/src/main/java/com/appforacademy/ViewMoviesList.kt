package com.appforacademy

import com.android.academy.fundamentals.homework.features.data.Movie

interface ViewMoviesList {


    fun loadMoviesInList()
    fun openMoviesDetallNew(data: Movie)
}