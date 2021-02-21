package com.android.academy.fundamentals.homework.features.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var title: String,
    var overview: String,
    var poster: String,
    var backdrop: String,
    var ratings: Float,
    var numberOfRatings: Int,
    var minimumAge: Int,
    var runtime: Int,

    var genres: List<Genre>,

    var actors: List<Actor>
) : Parcelable
