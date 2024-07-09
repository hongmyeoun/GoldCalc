package com.hongmyeoun.goldcalc.model.constants

object ErrorMessage {
    const val ERROR_503 = "서버 응답 실패: 503"
    const val ERROR_503_MESSAGE = "서버 점검중 입니다 ㅠㅠ"
    const val NO = "없는"

    fun noResult(input: String): String {
        return "\"${input}\"(은)는 없는 결과입니다."
    }

    fun serverError(error: Int): String {
        return "서버 응답 실패: $error"
    }
}