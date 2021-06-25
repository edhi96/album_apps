package com.sarwoedhi.albumapps.data.source

import com.sarwoedhi.albumapps.BuildConfig.API_KEY
import com.sarwoedhi.albumapps.data.response.MovieResponse
import com.sarwoedhi.albumapps.data.source.remote.api.MovieApi
import com.sarwoedhi.albumapps.data.source.remote.api.DownloadApi
import okhttp3.ResponseBody
import retrofit2.Call


class AlbumRepository constructor(private val movieApi: MovieApi, private val downloadApi: DownloadApi) :
    AlbumDataSource {
    override fun getAllMovies(): Call<MovieResponse> {
        return movieApi.getListMovie(apiKey=API_KEY)
    }

    override fun downloadPicture(path: String?): Call<ResponseBody> {
        return downloadApi.downloadFile(path)
    }

}