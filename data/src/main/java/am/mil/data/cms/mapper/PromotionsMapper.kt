package am.mil.data.cms.mapper

import am.mil.data.base.Mapper
import am.mil.data.cms.dto.response.PromotionDto
import am.mil.domain.cms.model.Promotion
import am.mil.domain.cms.model.ShowToTypesEnum
import retrofit2.Response

object PromotionsMapper {

    val promotionMapperResponse: Mapper<Response<PromotionDto?>, Response<Promotion>> = { response ->
            Response.success(
                Promotion(
                    id = response.body()?.data?.id,
                    showTo = ShowToTypesEnum.from(response.body()?.data?.showTo),
                    target = response.body()?.data?.target,
                    title = response.body()?.data?.title,
                    optIn = response.body()?.data?.optIn,
                    content = response.body()?.data?.content,
                    srcType = response.body()?.data?.srcType,
                    src = response.body()?.data?.src,
                    href = response.body()?.data?.href.toString(),
                    endDate = response.body()?.data?.endDate
                )
            )
        }


    val promotionMapper: Mapper<PromotionDto?, Promotion> = { response ->
        Promotion(
            id = response?.data?.id,
            showTo = ShowToTypesEnum.from(response?.data?.showTo),
            target = response?.data?.target,
            title = response?.data?.title,
            optIn = response?.data?.optIn,
            content = response?.data?.content,
            srcType = response?.data?.srcType,
            src = response?.data?.src,
            href = response?.data?.href.toString(),
            endDate = response?.data?.endDate
        )
    }
}