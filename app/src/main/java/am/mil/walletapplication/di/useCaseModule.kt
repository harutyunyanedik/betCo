package am.mil.walletapplication.di

import am.mil.domain.category.usecase.GetCategoriesUseCase
import am.mil.domain.history.usecase.GetTransactionsHistoryUseCase
import am.mil.domain.wallet.usecase.GetBalanceUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        GetCategoriesUseCase(get(), get())
    }

    factory {
        GetBalanceUseCase(get(), get())
    }

    factory {
        GetTransactionsHistoryUseCase(get(), get())
    }
}