package com.hongmyeoun.goldcalc.model.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import java.text.NumberFormat
import java.util.regex.Pattern

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

fun htmlStyledText(htmlText: String) : AnnotatedString {
    val annotatedString = buildAnnotatedString {
        val regex = Pattern.compile("<FONT COLOR='#(.*?)'>(.*?)</FONT>", Pattern.CASE_INSENSITIVE)
        val matcher = regex.matcher(htmlText)

        var lastIndex = 0
        while (matcher.find()) {
            val colorHex = matcher.group(1) ?: "#000000"
            val text = matcher.group(2) ?: ""

            // Append text before the match
            if (matcher.start() > lastIndex) {
                append(htmlText.substring(lastIndex, matcher.start()))
            }

            // Append the colored text
            val color = Color(android.graphics.Color.parseColor("#$colorHex"))
            withStyle(style = SpanStyle(color = color)) {
                append(text)
            }

            lastIndex = matcher.end()
        }

        // Append remaining text after the last match
        if (lastIndex < htmlText.length) {
            append(htmlText.substring(lastIndex))
        }
    }

    return annotatedString
}