package com.sarwoedhi.albumapps.di

import com.sarwoedhi.albumapps.data.source.AlbumRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { AlbumRepository(get(),get()) }
}