package com.hongmyeoun.goldcalc.model.constants

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
}