package com.sarwoedhi.albumapps.data.source.local.favorite

import com.sarwoedhi.albumapps.data.models.Movie

interface FavoriteDataSource {

    fun getListMovieFavorite(keyMovie:String):MutableList<Movie>

    fun getMovieFromFavorite(keyMovie:String,movie:Movie):Boolean

    fun saveMovieToFavorite(keyMovie:String,data: Movie)

    fun deleteFromFavorite(keyMovie:String,data: Movie):Boolean
}