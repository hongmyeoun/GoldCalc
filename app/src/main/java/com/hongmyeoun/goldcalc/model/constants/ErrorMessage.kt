package com.hongmyeoun.goldcalc.model.constants

object ErrorMessage {
    fun noResult(input: String): String {
        return "\"${input}\"(은)는 없는 결과입니다."
    }

    fun serverError(error: Int): String {
        return "서버 응답 실패: $error"
    }
}