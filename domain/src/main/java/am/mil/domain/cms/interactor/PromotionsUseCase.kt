package am.mil.domain.cms.interactor

import am.mil.domain.base.DispatcherProvider
import am.mil.domain.base.UseCaseWithParams
import am.mil.domain.cms.model.Promotion
import am.mil.domain.cms.repo.CmsRepository

class PromotionsUseCase(
    private val cmsRepository: CmsRepository,
    dispatcherProvider: DispatcherProvider
) : UseCaseWithParams<PromotionsUseCase.Params, Promotion>(dispatcherProvider.io) {

    data class Params(
        val cmsApiVersion: Int?,
        val language: String?,
        val partner: Int?,
        val promotionId: String?
    )

    override suspend fun execute(parameters: Params?): Promotion {
        return cmsRepository.getPromotion(parameters?.cmsApiVersion, parameters?.language, parameters?.partner, parameters?.promotionId)
    }
}