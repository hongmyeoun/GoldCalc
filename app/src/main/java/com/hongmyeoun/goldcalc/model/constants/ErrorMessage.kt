package com.hongmyeoun.goldcalc.model.constants

object ErrorMessage {
    const val ERROR_503 = "서버 응답 실패: 503"
    const val ERROR_503_MESSAGE = "서버 점검중 입니다 ㅠㅠ"
    const val NO = "없는"
    const val SEASON_3_NO_RECENT_CONNECT = "최근 캐릭터 접속 기록이 없습니다."
    const val RECONNECT = "게임에 접속해 주세요."

    fun noResult(input: String): String {
        return "\"${input}\"(은)는 없는 결과입니다."
    }

    fun serverError(error: Int): String {
        return "서버 응답 실패: $error"
    }
}