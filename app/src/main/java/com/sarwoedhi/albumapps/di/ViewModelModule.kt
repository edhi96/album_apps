package com.sarwoedhi.albumapps.di

import com.sarwoedhi.albumapps.ui.main.MainViewModel
import com.sarwoedhi.albumapps.ui.main.favorite.FavoriteViewModel
import com.sarwoedhi.albumapps.ui.main.home.HomeViewModel
import com.sarwoedhi.albumapps.ui.main.movie.MovieViewModel
import com.sarwoedhi.albumapps.ui.main.tv_show.TvShowViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { MovieViewModel() }
    viewModel { TvShowViewModel() }
    viewModel { FavoriteViewModel() }
}