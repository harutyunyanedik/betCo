package am.mil.presentationapp.di

import am.mil.domain.base.DispatcherProvider
import kotlinx.coroutines.Dispatchers
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
}