package com.nagyrobi144.dogify.android.di

import com.nagyrobi144.dogify.android.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
}

val appModule = listOf(viewModelModule)

