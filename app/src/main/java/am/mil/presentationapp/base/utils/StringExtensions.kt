package am.mil.presentationapp.base.utils

import am.mil.presentationapp.base.utils.WalletConstants.ZERO

fun String?.toDoubleOrZero() = this?.toDoubleOrNull() ?: ZERO.toDouble()