package am.mil.presentationapp.di

import am.mil.data.base.createOkHttpClient
import am.mil.data.base.createWebService
import am.mil.data.cms.net.CmsDataSource
import am.mil.data.cms.repo.CmsRepositoryImpl
import am.mil.domain.cms.repo.CmsRepository
import org.koin.dsl.module

internal val apiModule = module {

    single { createWebService<CmsDataSource>(createOkHttpClient(), "https://cmsbetconstruct.com/") }

    single<CmsRepository> { CmsRepositoryImpl(get()) }
}