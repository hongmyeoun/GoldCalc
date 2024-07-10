package com.hongmyeoun.goldcalc.model.constants.viewConst

object EquipmentConsts {
    const val WEAPON = "무기"
    const val HEAD = "투구"
    const val CHEST = "상의"
    const val PANTS = "하의"
    const val GLOVES = "장갑"
    const val SHOULDER = "어깨"
    val EQUIPMENT_LIST = listOf(WEAPON, HEAD, CHEST, PANTS, GLOVES, SHOULDER)

    const val NECKLACE = "목걸이"
    const val EARRINGS = "귀걸이"
    const val RING = "반지"
    val ACCESSORY_LIST = listOf(NECKLACE, EARRINGS, RING)

    const val ABILITY_STONE = "어빌리티 스톤"
    const val ABILITY_STONE_SHORT = "스톤"

    const val BRACELET = "팔찌"

    const val COMBAT_STAT = "전투 특성"
    const val CRIT = "치명"
    const val SPECIALIZATION = "특화"
    const val DOMINATION = "제압"
    const val SWIFTNESS = "신속"
    const val ENDURANCE = "인내"
    const val EXPERTISE = "숙련"
    val COMBAT_STAT_LIST = listOf(CRIT, SPECIALIZATION, DOMINATION, SWIFTNESS, ENDURANCE, EXPERTISE)
    val FILTERED_COMBAT_STAT_LIST = listOf(CRIT, SPECIALIZATION, SWIFTNESS)

    const val DEFAULT_STAT = "기본 특성"
    const val ATTACK_POWER = "공격력"
    const val MAX_LIFE = "최대 생명력"
    val DEFAULT_STAT_LIST = listOf(ATTACK_POWER, MAX_LIFE)

    const val DEAL_GEM_2_TIER = "청명"
    const val COOLTIME_GEM_2_TIER = "원해"

    const val DEAL_GEM_3_TIER = "멸화"
    const val COOLTIME_GEM_3_TIER = "홍염"

    const val DEAL_GEM_4_TIER = "겁화"
    const val COOLTIME_GEM_4_TIER = "작열"

    const val DEAL_GEM_2_TIER_SHORT = '청'
    const val COOLTIME_GEM_2_TIER_SHORT = '원'

    const val DEAL_GEM_3_TIER_SHORT = '멸'
    const val COOLTIME_GEM_3_TIER_SHORT = '홍'

    const val DEAL_GEM_4_TIER_SHORT = '겁'
    const val COOLTIME_GEM_4_TIER_SHORT = '작'

    val DEAL_GEM_LIST = listOf(DEAL_GEM_2_TIER, DEAL_GEM_3_TIER, DEAL_GEM_4_TIER)
    val COOLTIME_GEM_LIST = listOf(COOLTIME_GEM_2_TIER, COOLTIME_GEM_3_TIER, COOLTIME_GEM_4_TIER)

    const val DEAL = "피해"
    const val COOLTIME = "재사용 대기시간"
    const val COOLTIME_SHORT = "쿨타임"

    const val INCREASE = "증가"

    const val DEFAULT_SPACE = "기본 "

    const val GRADE_NORMAL = "일반"
    const val GRADE_UNCOMMON = "고급"
    const val GRADE_RARE = "희귀"
    const val GRADE_EPIC = "영웅"
    const val GRADE_LEGENDARY = "전설"
    const val GRADE_RELIC = "유물"
    const val GRADE_ANCIENT = "고대"
    const val GRADE_ESTHER = "에스더"

    const val NO_SETTING = "세트 없음"

}