package am.mil.presentationapp.di

import am.mil.presentationapp.HomeActivity
import am.mil.presentationapp.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val viewModelModule = module {

    scope(named<HomeActivity>()) {
        viewModel { HomeViewModel(get(), get(), get()) }
    }
}