package com.appforacademy.data

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.internal.*


@Serializable
data class ApiMovie (
    val adult: Boolean? = null,

    @SerialName("backdrop_path")
    val backdrop_path: String? = null,

    @SerialName("belongs_to_collection")
    val belongs_to_collection: BelongsToCollection? = null,

    val budget: Long? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Long? = null,

    @SerialName("imdb_id")
    val imdb_id: String? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_title")
    val original_title: String? = null,

    val overview: String? = null,
    val popularity: Double? = null,

    @SerialName("poster_path")
    val poster_path: String? = null,

    @SerialName("production_companies")
    val production_companies: List<ProductionCompany>? = null,

    @SerialName("production_countries")
    val production_countries: List<ProductionCountry>? = null,

    @SerialName("release_date")
    val release_date: String? = null,

    val revenue: Long? = null,
    val runtime: Long? = null,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = null,

    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,

    @SerialName("vote_average")
    val vote_average: Double? = null,

    @SerialName("vote_count")
    val vote_count: Long? = null
)

@Serializable
data class BelongsToCollection (
    val id: Long? = null,
    val name: String? = null,

    @SerialName("poster_path")
    val poster_path: String? = null,

    @SerialName("backdrop_path")
    val backdrop_path: String? = null
)

@Serializable
data class Genre (
    val id: Long? = null,
    val name: String? = null
)

@Serializable
data class ProductionCompany (
    val id: Long? = null,

    @SerialName("logo_path")
    val logo_path: String? = null,

    val name: String? = null,

    @SerialName("origin_country")
    val origin_country: String? = null
)

@Serializable
data class ProductionCountry (
    @SerialName("iso_3166_1")
    val iso_3166_1: String? = null,

    val name: String? = null
)

@Serializable
data class SpokenLanguage (
    @SerialName("english_name")
    val english_name: String? = null,

    @SerialName("iso_639_1")
    val iso_639_1: String? = null,

    val name: String? = null
)
