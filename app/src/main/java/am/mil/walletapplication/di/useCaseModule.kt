package am.mil.walletapplication.di

import am.mil.domain.category.usecase.GetCategoriesUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        GetCategoriesUseCase(get(), get())
    }
}