package am.mil.domain.cms.repo

import am.mil.domain.cms.model.Promotion
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CmsRepository {

    suspend fun getPromotionCallback(cmsApiVersion: Int?, language: String?, partner: Int?, promotionId: String?): Response<Promotion>

    suspend fun getPromotion(cmsApiVersion: Int?, language: String?, partner: Int?, promotionId: String?): Promotion

    fun getPromotionFlow(cmsApiVersion: Int?, language: String?, partner: Int?, promotionId: String?): Flow<Promotion>
}