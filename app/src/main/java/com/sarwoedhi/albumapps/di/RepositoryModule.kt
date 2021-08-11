package com.sarwoedhi.albumapps.di

import com.sarwoedhi.albumapps.data.source.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepository(get()) }
}