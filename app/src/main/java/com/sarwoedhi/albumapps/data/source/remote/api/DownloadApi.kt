package com.sarwoedhi.albumapps.data.source.remote.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DownloadApi {

    @GET
    @Streaming
   fun downloadFile(@Url path:String?): Call<ResponseBody>

}