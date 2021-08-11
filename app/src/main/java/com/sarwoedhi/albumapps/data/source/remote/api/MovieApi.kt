package com.sarwoedhi.albumapps.data.source.remote.api

import com.sarwoedhi.albumapps.data.response.DetailMovieResponse
import com.sarwoedhi.albumapps.data.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/now_playing")
    fun getListMovie(@Query("api_key")apiKey:String): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id")movieId:String,@Query("api_key")apiKey:String): Call<DetailMovieResponse>
}