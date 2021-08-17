package com.sarwoedhi.albumapps.data.source.local.favorite

import android.content.Context
import com.google.gson.Gson
import com.sarwoedhi.albumapps.data.models.Movie
import com.sarwoedhi.albumapps.data.source.local.shared_preference.SharedPreference

class FavoriteRepository (context: Context,gson: Gson): SharedPreference(context,gson),FavoriteDataSource {

    private val listMovie = mutableListOf<Movie>()

    override fun getPreferencesGroup(): String = "MOVIE_APP"

    override fun getListMovieFavorite(keyMovie: String): MutableList<Movie> {
        return getDataList(keyMovie, Movie::class.java)?: mutableListOf()
    }

    override fun getMovieFromFavorite(keyMovie: String,movie:Movie): Boolean {
        var conditionsMovie = false
        val listData = getDataList(keyMovie,Movie::class.java)
        if(listData!=null && listData.isNotEmpty()){
            conditionsMovie = listData.any { it == movie }
        }
        return conditionsMovie
    }

    override fun saveMovieToFavorite(keyMovie:String,data: Movie) {
        listMovie.add(data)
        val set = mutableSetOf<Movie>()
        set.addAll(getDataList(keyMovie,Movie::class.java)?: mutableListOf())
        listMovie.addAll(set)
        saveDataList(keyMovie, listMovie)
    }

    override fun deleteFromFavorite(keyMovie:String,data: Movie):Boolean {
        var conditionsDeleted = false
        val listData = getDataList(keyMovie,Movie::class.java)
        val temptListData = mutableListOf<Movie>()
        val idx: Int
        if(listData!=null && listData.isNotEmpty()){
            idx = listData.indexOf(data)
            if(idx!= -1) {
                listData.remove(data)
                temptListData.addAll(listData)
                saveDataList(keyMovie, temptListData)
                conditionsDeleted = true
            }
        }
        return conditionsDeleted
    }


}