package com.sarwoedhi.albumapps.data.source

import com.sarwoedhi.albumapps.data.response.DetailMovieResponse
import com.sarwoedhi.albumapps.data.response.MovieResponse
import retrofit2.Call

interface MovieDataSource {

    fun getAllMovies(): Call<MovieResponse>

    fun getDetailMovies(id:String): Call<DetailMovieResponse>

}