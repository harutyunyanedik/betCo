package am.mil.walletapplication.di

import am.mil.domain.menu.usecase.GetMenuItemsUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        GetMenuItemsUseCase(get(), get())
    }
}