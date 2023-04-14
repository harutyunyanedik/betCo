package am.mil.walletapplication.di

import am.mil.data.base.createOkHttpClient
import am.mil.data.base.createWebService
import am.mil.data.net.categories.CategoriesSharedRepoImpl
import am.mil.data.net.categories.CategoriesDataSource
import am.mil.domain.category.repo.CategoriesSharedRepo
import org.koin.dsl.module

internal val apiModule = module {

    single { createWebService<CategoriesDataSource>(createOkHttpClient(), "https://dev-workflow-elements-api.azurewebsites.net/") }

    single<CategoriesSharedRepo> { CategoriesSharedRepoImpl(get()) }
}