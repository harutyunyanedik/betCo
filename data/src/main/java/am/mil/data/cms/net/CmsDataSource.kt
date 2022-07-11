package am.mil.data.cms.net

import am.mil.data.cms.dto.response.PromotionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CmsDataSource {

    @GET("api/public/v{cms_api_version}/{language}/partners/{partner_id}/promotions/{promotion_id}")
    suspend fun getPromotionResponse(
        @Header("Accept") accept: String = "application/json",
        @Header("Content-Type") contentType: String = "application/json",
        @Path("cms_api_version") cmsApiVersion: Int?,
        @Path("language") language: String?,
        @Path("partner_id") partner: Int?,
        @Path("promotion_id") promotionId: String?
    ): Response<PromotionDto?>


    @GET("api/public/v{cms_api_version}/{language}/partners/{partner_id}/promotions/{promotion_id}")
    suspend fun getPromotion(
        @Header("Accept") accept: String = "application/json",
        @Header("Content-Type") contentType: String = "application/json",
        @Path("cms_api_version") cmsApiVersion: Int?,
        @Path("language") language: String?,
        @Path("partner_id") partner: Int?,
        @Path("promotion_id") promotionId: String?
    ): PromotionDto?
}