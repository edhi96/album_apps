package com.sarwoedhi.albumapps.data.source.remote.api

import com.sarwoedhi.albumapps.data.models.body.LoginBody
import com.sarwoedhi.albumapps.data.response.DetailMovieResponse
import com.sarwoedhi.albumapps.data.response.MovieResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface MovieApi {

    @GET("movie/now_playing")
    fun getListMovie(@Query("api_key")apiKey:String): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id")movieId:String,@Query("api_key")apiKey:String): Call<DetailMovieResponse>

    @GET("search/movie?")
    fun getSearchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") languages: String,
        @Query("query") searchQuery: String
    ): Call<MovieResponse>

    //Just Example of Post
    @POST("users/login")
    fun login(@Body loginBody: LoginBody): Call<DetailMovieResponse>

    @Multipart
    @POST("users/me")
    fun updateProfileWithPhoto(@Header("Authorization") authorization: String,
                               @Part("_method") methodUpdate: RequestBody? = null,
                               @Part("name") name: RequestBody? = null,
                               @Part("email") email: RequestBody? = null,
                               @Part("phone") phoneNumber: RequestBody? = null,
                               @Part photo: MultipartBody.Part): Call<DetailMovieResponse>
}