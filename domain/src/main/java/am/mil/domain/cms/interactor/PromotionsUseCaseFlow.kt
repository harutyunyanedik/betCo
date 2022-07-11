package am.mil.domain.cms.interactor

import am.mil.domain.base.DispatcherProvider
import am.mil.domain.base.FlowUseCase
import am.mil.domain.cms.model.Promotion
import am.mil.domain.cms.repo.CmsRepository
import kotlinx.coroutines.flow.Flow

class PromotionsUseCaseFlow(
    private val cmsRepository: CmsRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<PromotionsUseCaseFlow.Params, Promotion>(dispatcherProvider.io) {

    data class Params(
        val cmsApiVersion: Int?,
        val language: String?,
        val partner: Int?,
        val promotionId: String?
    )

    override fun execute(parameters: Params): Flow<Promotion> {
        return cmsRepository.getPromotionFlow(parameters.cmsApiVersion, parameters.language, parameters.partner, parameters.promotionId)
    }
}

