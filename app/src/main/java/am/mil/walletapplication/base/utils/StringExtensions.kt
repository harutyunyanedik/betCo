package am.mil.walletapplication.base.utils

import am.mil.walletapplication.base.utils.WalletConstants.ZERO

fun String?.toDoubleOrZero() = this?.toDoubleOrNull() ?: ZERO.toDouble()