package com.sarwoedhi.albumapps.data.source

import com.sarwoedhi.albumapps.BuildConfig.API_KEY
import com.sarwoedhi.albumapps.data.response.DetailMovieResponse
import com.sarwoedhi.albumapps.data.response.MovieResponse
import com.sarwoedhi.albumapps.data.source.remote.api.MovieApi
import retrofit2.Call


class MovieRepository constructor(private val movieApi: MovieApi) :
    MovieDataSource {

    override fun getAllMovies(): Call<MovieResponse> {
        return movieApi.getListMovie(apiKey=API_KEY)
    }

    override fun getDetailMovies(id: String): Call<DetailMovieResponse> {
        return movieApi.getDetailMovie(movieId = id,apiKey=API_KEY)
    }

}