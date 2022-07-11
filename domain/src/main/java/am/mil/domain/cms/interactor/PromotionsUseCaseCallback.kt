package am.mil.domain.cms.interactor

import am.mil.domain.base.CallbackUseCase
import am.mil.domain.base.DispatcherProvider
import am.mil.domain.cms.model.Promotion
import am.mil.domain.cms.repo.CmsRepository
import retrofit2.Response

class PromotionsUseCaseCallback(
    private val cmsRepository: CmsRepository,
    dispatcherProvider: DispatcherProvider
) : CallbackUseCase<PromotionsUseCaseCallback.Params, Promotion>(dispatcherProvider.io) {

    data class Params(
        val cmsApiVersion: Int?,
        val language: String?,
        val partner: Int?,
        val promotionId: String?
    )

    override suspend fun execute(parameters: Params?): Response<Promotion> {
        return cmsRepository.getPromotionCallback(parameters?.cmsApiVersion, parameters?.language, parameters?.partner, parameters?.promotionId)
    }
}