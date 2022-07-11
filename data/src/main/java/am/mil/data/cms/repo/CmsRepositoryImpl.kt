package am.mil.data.cms.repo

import am.mil.data.cms.mapper.PromotionsMapper
import am.mil.data.cms.net.CmsDataSource
import am.mil.domain.cms.model.Promotion
import am.mil.domain.cms.repo.CmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class CmsRepositoryImpl(private val dataSource: CmsDataSource) : CmsRepository {

    override suspend fun getPromotionCallback(
        cmsApiVersion: Int?,
        language: String?,
        partner: Int?,
        promotionId: String?
    ): Response<Promotion> = dataSource.getPromotionResponse(
        cmsApiVersion = cmsApiVersion,
        language = language,
        partner = partner,
        promotionId = promotionId
    ).let(PromotionsMapper.promotionMapperResponse)

    override suspend fun getPromotion(
        cmsApiVersion: Int?,
        language: String?,
        partner: Int?,
        promotionId: String?
    ): Promotion = dataSource.getPromotion(
        cmsApiVersion = cmsApiVersion,
        language = language,
        partner = partner,
        promotionId = promotionId
    ).let(PromotionsMapper.promotionMapper)


    override fun getPromotionFlow(
        cmsApiVersion: Int?,
        language: String?,
        partner: Int?,
        promotionId: String?
    ): Flow<Promotion> =
        flow {
            val promotion = dataSource.getPromotion(
                cmsApiVersion = cmsApiVersion,
                language = language,
                partner = partner,
                promotionId = promotionId
            ).let(PromotionsMapper.promotionMapper)
            emit(promotion)
        }
}