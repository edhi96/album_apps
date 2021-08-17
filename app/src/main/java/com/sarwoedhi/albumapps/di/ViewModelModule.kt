package com.sarwoedhi.albumapps.di

import com.sarwoedhi.albumapps.ui.detail.DetailViewModel
import com.sarwoedhi.albumapps.ui.main.MainViewModel
import com.sarwoedhi.albumapps.ui.main.favorite.favorite_movie.FavoriteMovieViewModel
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
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}