package com.hongmyeoun.goldcalc.model.common

import java.text.NumberFormat

fun Int.formatWithCommas(): String {
    val numberFormat = NumberFormat.getNumberInstance()
    return numberFormat.format(this)
}

fun Long.formatWithCommas(): String {
    val numberFormat = NumberFormat.getNumberInstance()
    return numberFormat.format(this)
}

fun String.formatWithCommas(): String {
    val number = this.toIntOrNull() ?: return this
    val numberFormat = NumberFormat.getNumberInstance()
    return numberFormat.format(number)
}

fun Float.toPercentage(): String {
    return "${(this * 100).toInt()}%"
}