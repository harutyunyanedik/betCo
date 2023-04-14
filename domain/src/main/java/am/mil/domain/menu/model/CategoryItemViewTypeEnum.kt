package am.mil.domain.menu.model

enum class CategoryItemViewTypeEnum(val type: Int) {
    GRID(0),
    LINEAR(1);

    companion object {
        private val map = values().associateBy(CategoryItemViewTypeEnum::type)
        fun from(type: Int?) = map[type] ?: GRID
    }
}