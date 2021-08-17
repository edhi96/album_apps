package com.sarwoedhi.albumapps.di

import com.google.gson.Gson
import com.sarwoedhi.albumapps.data.source.local.favorite.FavoriteRepository
import com.sarwoedhi.albumapps.data.source.remote.movie.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { Gson() }
    factory { MovieRepository(get()) }
    factory { FavoriteRepository(get(),get()) }
}