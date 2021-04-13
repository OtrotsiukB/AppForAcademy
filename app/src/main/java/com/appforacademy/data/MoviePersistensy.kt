package com.appforacademy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class MoviePersistensy(@PrimaryKey
                            var id: Int,
                            var title: String,
                            var overview: String,
                            var poster: String,
                            var backdrop: String,
                            var ratings: Float,
                            var numberOfRatings: Int,
                            var minimumAge: Int,
                            var runtime: Int)
