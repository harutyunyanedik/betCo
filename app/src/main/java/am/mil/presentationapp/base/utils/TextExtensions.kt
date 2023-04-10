package am.mil.presentationapp.base.utils

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan

fun SpannableString.color(color: Int, start: Int?, end: Int?): SpannableString {
    var spanStartPoint = 0
    var spanEndPoint = 0
    if (start != null) spanStartPoint = start
    if (end != null) spanEndPoint = end
    this.setSpan(ForegroundColorSpan(color), spanStartPoint, spanEndPoint, 0)
    return this
}

fun SpannableString.bold(start: Int?, end: Int?): SpannableString {
    var spanStartPoint = 0
    var spanEndPoint = 0
    if (start != null) spanStartPoint = start
    if (end != null) spanEndPoint = end
    this.setSpan(StyleSpan(Typeface.BOLD), spanStartPoint, spanEndPoint, 0)
    return this
}

fun SpannableString.underline(start: Int?, end: Int?): SpannableString {
    var spanStartPoint = 0
    var spanEndPoint = 0
    if (start != null) spanStartPoint = start
    if (end != null) spanEndPoint = end
    this.setSpan(UnderlineSpan(), spanStartPoint, spanEndPoint, 0)
    return this
}

fun SpannableString.italic(start: Int?, end: Int?): SpannableString {
    var spanStartPoint = 0
    var spanEndPoint = 0
    if (start != null) spanStartPoint = start
    if (end != null) spanEndPoint = end
    this.setSpan(StyleSpan(Typeface.ITALIC), spanStartPoint, spanEndPoint, 0)
    return this
}

fun SpannableString.strike(start: Int?, end: Int?): SpannableString {
    var spanStartPoint = 0
    var spanEndPoint = 0
    if (start != null) spanStartPoint = start
    if (end != null) spanEndPoint = end
    this.setSpan(StrikethroughSpan(), spanStartPoint, spanEndPoint, 0)
    return this
}

fun String.spannableColor(color: Int, spannableText: String): SpannableString = SpannableString(this).color(color, this.length - spannableText.length, this.length)

fun String.spannableUnderline(start: Int?, end: Int?): SpannableString = SpannableString(this).underline(start, end)
