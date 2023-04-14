package am.mil.walletapplication.base.utils

import am.mil.domain.menu.model.CategoryItemViewTypeEnum

object WalletPreferencesManager {

    private const val PREF_CATEGORY_ITEM_VIEW_TYPE = "pref_category_item_view_type"
    private const val PREF_IS_SHOW_BALANCE = "pref_is_show_balance"

    fun putCategoryItemViewType(categoryItemViewTypeEnum: CategoryItemViewTypeEnum) = Prefs.put(PREF_CATEGORY_ITEM_VIEW_TYPE, categoryItemViewTypeEnum.type)

    fun getCategoryItemViewType(): CategoryItemViewTypeEnum = CategoryItemViewTypeEnum.from(Prefs.getInt(PREF_CATEGORY_ITEM_VIEW_TYPE, CategoryItemViewTypeEnum.GRID.type))

    fun putIsShowBalance(isShow: Boolean) = Prefs.put(PREF_IS_SHOW_BALANCE, isShow)

    fun isShowBalance() = Prefs.getBoolean(PREF_IS_SHOW_BALANCE, false)

}