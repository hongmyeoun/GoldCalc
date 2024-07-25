package com.hongmyeoun.goldcalc.model.constants.raid

class Raid {
    object Name {
        const val COMMAND = "군단장"
        const val COMMAND_RAID = "군단장 레이드"
        const val VALTAN = "발탄"
        const val BIACKISS = "비아키스"
        const val KOUKU_SATON = "쿠크세이튼"
        const val ABRELSHUD = "아브렐슈드"
        const val ILLIAKAN = "일리아칸"
        const val KAMEN = "카멘"

        const val BIACKISS_SHORT = "비아"
        const val KOUKU_SATON_SHORT = "쿠크"
        const val ABRELSHUD_SHORT = "아브"
        const val ILLIAKAN_SHORT = "일리"

        const val ABYSS_DUNGEON = "어비스 던전"
        const val KAYANGEL = "카양겔"
        const val IVORY_TOWER = "상아탑"

        const val IVORY_TOWER_LONG = "혼돈의 상아탑"

        const val KAZEROTH = "카제로스"
        const val KAZEROTH_RAID = "카제로스 레이드"
        const val ECHIDNA = "에키드나"
        const val EGIR = "에기르"

        const val EPIC = "에픽"
        const val EPIC_RAID = "에픽 레이드"
        const val BEHEMOTH = "베히모스"

        const val ETC = "기타"

        val RAID_LIST = listOf(COMMAND, ABYSS_DUNGEON, KAZEROTH, EPIC, ETC)
    }

    object Difficulty {
        const val NORMAL = "normal"
        const val HARD = "hard"
        const val SOLO = "solo"

        const val KR_NORMAL = "노말"
        const val KR_HARD = "하드"
        const val KR_SOLO = "솔로잉"
    }
}