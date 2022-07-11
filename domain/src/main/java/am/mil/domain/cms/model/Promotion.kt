package am.mil.domain.cms.model

data class Promotion(
    val id: Int? = null,
    val showTo: ShowToTypesEnum? = null,
    val target: String? = null,
    val optIn: String? = null,
    val title: String? = null,
    val content: String? = null,
    val srcType: String? = null,
    val src: String? = null,
    val href: String? = null,
    val endDate: String? = null
)