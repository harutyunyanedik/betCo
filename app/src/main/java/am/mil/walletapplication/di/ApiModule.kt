package am.mil.walletapplication.di

import am.mil.data.base.createOkHttpClient
import am.mil.data.base.createWebService
import am.mil.data.menu.repo.MenuItemRepoImpl
import am.mil.data.net.WalletDataSource
import am.mil.domain.menu.repo.MenuItemRepo
import org.koin.dsl.module

internal val apiModule = module {

    single { createWebService<WalletDataSource>(createOkHttpClient(), "https://dev-workflow-elements-api.azurewebsites.net/") }

    single<MenuItemRepo> { MenuItemRepoImpl(get()) }
}