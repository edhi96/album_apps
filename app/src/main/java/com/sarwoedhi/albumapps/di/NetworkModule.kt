package com.sarwoedhi.albumapps.di

import com.sarwoedhi.albumapps.data.source.remote.network.NetworkService
import org.koin.dsl.module

val networkModule = module {
    single { NetworkService() }
}