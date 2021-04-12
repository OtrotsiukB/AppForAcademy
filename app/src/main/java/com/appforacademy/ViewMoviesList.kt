package com.appforacademy

import com.android.academy.fundamentals.homework.features.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ViewMoviesList {


    fun loadMoviesInList()
    fun openMoviesDetallNew(data: Movie)
    suspend fun loadMoviesInListFromOnline(inMovieList:List<Movie>)
}