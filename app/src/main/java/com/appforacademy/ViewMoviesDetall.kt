package com.appforacademy

import com.android.academy.fundamentals.homework.features.data.Movie

interface ViewMoviesDetall {


   fun addInfoMovieDetal(movieData: Movie?)
   fun openActorAdapter(movieData:Movie?)

}