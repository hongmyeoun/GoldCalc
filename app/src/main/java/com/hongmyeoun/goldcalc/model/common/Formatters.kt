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

fun htmlStyledText(htmlText: String): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        // 정규식 패턴: <FONT COLOR=''> 태그 찾기
        val fontRegex = Pattern.compile("<FONT COLOR='#(.*?)'>(.*?)</FONT>", Pattern.CASE_INSENSITIVE)
        val matcher = fontRegex.matcher(htmlText)

        var lastIndex = 0
        while (matcher.find()) {
            val colorHex = matcher.group(1) ?: "#000000"
            val text = matcher.group(2) ?: ""

            // 매칭 전에 있는 텍스트 처리 (태그 변환 및 줄바꿈 적용)
            if (matcher.start() > lastIndex) {
                val beforeMatchText = htmlText.substring(lastIndex, matcher.start())
                appendWithNewlineHandling(beforeMatchText)
            }

            // 매칭된 컬러 텍스트 처리
            val color = Color(android.graphics.Color.parseColor("#$colorHex"))
            withStyle(style = SpanStyle(color = color)) {
                append(text)
            }

            lastIndex = matcher.end()
        }

        // 마지막 매칭 후 남은 텍스트 처리 (태그 변환 및 줄바꿈 적용)
        if (lastIndex < htmlText.length) {
            val remainingText = htmlText.substring(lastIndex)
            appendWithNewlineHandling(remainingText)
        }
    }

    return annotatedString
}

// 줄바꿈 태그(<BR> 및 ||<BR>) 처리 함수
fun AnnotatedString.Builder.appendWithNewlineHandling(text: String) {
    // ||<BR>를 먼저 처리하고 그 후에 <BR>을 처리
    val newlineText = text
        .replace("||<BR>", "")  // ||<BR>을 줄바꿈 처리
        .replace("<BR> ", "\n")    // <BR>도 줄바꿈 처리

    append(newlineText)
}
