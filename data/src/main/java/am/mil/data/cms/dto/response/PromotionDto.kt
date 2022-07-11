package am.mil.data.cms.dto.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PromotionDto(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("text")
    val text: String?
) : Serializable

data class Data(
    @SerializedName("content")
    val content: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("first_deposit")
    val firstDeposit: Int?,
    @SerializedName("href")
    val href: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("opt_in")
    val optIn: String?,
    @SerializedName("profile_types")
    val profileTypes: List<Any>?,
    @SerializedName("show_to")
    val showTo: Int?,
    @SerializedName("src")
    val src: String?,
    @SerializedName("src_alt")
    val srcAlt: Any?,
    @SerializedName("src_type")
    val srcType: String?,
    @SerializedName("target")
    val target: String?,
    @SerializedName("title")
    val title: String?
) : Serializable