package am.mil.walletapplication.di

import am.mil.data.HistoryPagingDataSource
import am.mil.data.base.createOkHttpClient
import am.mil.data.base.createWebService
import am.mil.data.net.categories.CategoriesDataSource
import am.mil.data.net.categories.CategoriesSharedRepoImpl
import am.mil.data.net.history.HistoryDataSource
import am.mil.data.net.history.HistorySharedRepoImpl
import am.mil.data.net.wallet.WalletDataSource
import am.mil.data.net.wallet.balance.WalletSharedRepoImpl
import am.mil.domain.category.repo.CategoriesSharedRepo
import am.mil.domain.history.repo.HistorySharedRepo
import am.mil.domain.wallet.repo.WalletSharedRepo
import am.mil.walletapplication.base.utils.WalletConstants.CATEGORIES_BASE_URL
import am.mil.walletapplication.base.utils.WalletConstants.WALLET_PAYMENTS_BASE_URL
import org.koin.dsl.module

internal val apiModule = module {

    single { createWebService<CategoriesDataSource>(createOkHttpClient(), CATEGORIES_BASE_URL) }

    single<CategoriesSharedRepo> { CategoriesSharedRepoImpl(get()) }


    single { createWebService<WalletDataSource>(createOkHttpClient(), WALLET_PAYMENTS_BASE_URL) }

    single<WalletSharedRepo> { WalletSharedRepoImpl(get()) }

    single { createWebService<HistoryDataSource>(createOkHttpClient(), WALLET_PAYMENTS_BASE_URL) }

    single<HistorySharedRepo> { HistorySharedRepoImpl(get()) }
    single { HistoryPagingDataSource(get()) }
}