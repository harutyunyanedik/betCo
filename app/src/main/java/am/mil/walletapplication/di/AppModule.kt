package am.mil.walletapplication.di

import am.mil.domain.base.DispatcherProvider
import am.mil.walletapplication.HomeActivity
import am.mil.walletapplication.HomeViewModel
import am.mil.walletapplication.history.HistoryMainTabViewModel
import am.mil.walletapplication.home.HomeMainTabViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val appModule = module {

    single<DispatcherProvider> {
        object : DispatcherProvider {
            override val io = Dispatchers.IO
            override val default = Dispatchers.Default
            override val ui = Dispatchers.Main
            override val unconfined = Dispatchers.Unconfined
        }
    }

    scope(named<HomeActivity>()) {
        viewModel { HomeViewModel() }
    }

    viewModel { HomeMainTabViewModel(get(), get()) }
    viewModel { HistoryMainTabViewModel(get()) }
}