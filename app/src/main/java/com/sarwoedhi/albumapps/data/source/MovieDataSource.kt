package com.sarwoedhi.albumapps.data.source

import com.sarwoedhi.albumapps.data.response.DetailMovieResponse
import com.sarwoedhi.albumapps.data.response.MovieResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataSource {

    fun getAllMovies(): Call<MovieResponse>

    fun getDetailMovies(id:String): Call<DetailMovieResponse>

    fun postLogin(email:String,password:String,deviceToken:String): Call<DetailMovieResponse>

    fun updateProfileWithPhoto(token:String,nameProfile:String,emailProfile:String,phoneProfile:String,
                               profilePhoto: MultipartBody.Part): Call<DetailMovieResponse>

    fun getSearchMovies(searchQuery: String): Call<MovieResponse>
}