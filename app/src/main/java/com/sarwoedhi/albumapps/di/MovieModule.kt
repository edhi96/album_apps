package com.sarwoedhi.albumapps.di

import com.sarwoedhi.albumapps.data.source.remote.api.MovieApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { provideMovieApiService(get()) }
}

private fun provideMovieApiService(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)