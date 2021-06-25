package com.sarwoedhi.albumapps.di

import com.sarwoedhi.albumapps.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}