package com.sarwoedhi.albumapps.data.source

import com.sarwoedhi.albumapps.BuildConfig.API_KEY
import com.sarwoedhi.albumapps.data.models.body.LoginBody
import com.sarwoedhi.albumapps.data.response.DetailMovieResponse
import com.sarwoedhi.albumapps.data.response.MovieResponse
import com.sarwoedhi.albumapps.data.source.remote.api.MovieApi
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call


class MovieRepository constructor(private val movieApi: MovieApi) :
    MovieDataSource {

    override fun getAllMovies(): Call<MovieResponse> {
        return movieApi.getListMovie(apiKey=API_KEY)
    }

    override fun getDetailMovies(id: String): Call<DetailMovieResponse> {
        return movieApi.getDetailMovie(movieId = id,apiKey=API_KEY)
    }

    override fun postLogin(email: String, password: String, deviceToken: String): Call<DetailMovieResponse> {
        return movieApi.login(LoginBody(email = email,password=password,deviceToken))
    }

    override fun updateProfileWithPhoto(
        token: String,
        nameProfile: String,
        emailProfile: String,
        phoneProfile: String,
        profilePhoto: MultipartBody.Part
    ): Call<DetailMovieResponse> {
        val method: RequestBody = "PUT".toRequestBody("text/plain".toMediaTypeOrNull())
        val name: RequestBody = nameProfile.toRequestBody("text/plain".toMediaTypeOrNull())
        val email: RequestBody = emailProfile.toRequestBody("text/plain".toMediaTypeOrNull())
        val phone: RequestBody = phoneProfile.toRequestBody("text/plain".toMediaTypeOrNull())

        /* deprecated tp masih work
         val methodPUT: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "PUT")
        val nameProfile: RequestBody = RequestBody.create(MediaType.parse("text/plain"), name)
        val emailProfile: RequestBody = RequestBody.create(MediaType.parse("text/plain"), email)
        val phoneProfile: RequestBody = RequestBody.create(MediaType.parse("text/plain"), phone)
         */

        return movieApi.updateProfileWithPhoto(token,method,name,email,phone,profilePhoto)
    }

    override fun getSearchMovies(searchQuery: String): Call<MovieResponse> {
        return movieApi.getSearchMovies(apiKey = API_KEY,searchQuery = searchQuery,languages = "en")
    }

}