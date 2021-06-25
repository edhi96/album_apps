package com.sarwoedhi.albumapps.data.source

import com.sarwoedhi.albumapps.data.response.MovieResponse
import okhttp3.ResponseBody
import retrofit2.Call

interface AlbumDataSource {

    fun getAllMovies(): Call<MovieResponse>

    fun downloadPicture(path:String?):Call<ResponseBody>
}