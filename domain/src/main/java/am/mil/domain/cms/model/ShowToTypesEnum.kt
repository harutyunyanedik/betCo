package am.mil.domain.cms.model

enum class ShowToTypesEnum(val value: Int) {
    LOGGED_IN(0),
    GUEST(1),
    BOTH(2);

    companion object {
        private val map = values().associateBy(ShowToTypesEnum::value)
        fun from(value: Int?) = map[value]
    }
}