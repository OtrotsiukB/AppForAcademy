package com.appforacademy.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class APIPlaynow(
    val dates: Dates? = null,
    val page: Long? = null,
    val results: List<Result>? = null,

    @SerialName("total_pages")
    val totalPages: Long? = null,

    @SerialName("total_results")
    val totalResults: Long? = null
)

@Serializable
data class Dates (
    val maximum: String? = null,
    val minimum: String? = null
)

@Serializable
data class Result (
    val adult: Boolean? = null,

    @SerialName("backdrop_path")
    val backdrop_path: String? = null,

    @SerialName("genre_ids")
    val genreIDS: List<Long>? = null,

    val id: Long? = null,

    @SerialName("original_language")
    val originalLanguage: OriginalLanguage? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    val overview: String? = null,
    val popularity: Double? = null,

    @SerialName("poster_path")
    val poster_path: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    val title: String? = null,
    val video: Boolean? = null,

    @SerialName("vote_average")
    val vote_average: Double? = null,

    @SerialName("vote_count")
    val vote_count: Long? = null
)


@Serializable
enum class OriginalLanguage {
    En,
    Ja,
    Ru,
    Zh
}