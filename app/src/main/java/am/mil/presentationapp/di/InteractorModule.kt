package am.mil.presentationapp.di

import am.mil.domain.cms.interactor.PromotionsUseCaseCallback
import am.mil.domain.cms.interactor.PromotionsUseCaseFlow
import am.mil.domain.cms.interactor.PromotionsUseCase
import org.koin.dsl.module

val interactModule = module {

    factory {
        PromotionsUseCaseCallback(get(), get())
    }

    factory {
        PromotionsUseCase(get(), get())
    }

    factory {
        PromotionsUseCaseFlow(get(), get())
    }
}