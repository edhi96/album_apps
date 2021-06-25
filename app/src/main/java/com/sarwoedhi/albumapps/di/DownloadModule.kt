package com.sarwoedhi.albumapps.di

import com.sarwoedhi.albumapps.data.source.remote.api.DownloadApi

import org.koin.dsl.module
import retrofit2.Retrofit

val downloadModule = module {
    single { provideDownloadService(get()) }
}

private fun provideDownloadService(retrofit: Retrofit): DownloadApi = retrofit.create(DownloadApi::class.java)