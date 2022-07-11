package am.mil.presentationapp.di

import am.mil.presentationapp.MainActivity
import am.mil.presentationapp.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val viewModelModule = module {

    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get(), get(), get()) }
    }
}