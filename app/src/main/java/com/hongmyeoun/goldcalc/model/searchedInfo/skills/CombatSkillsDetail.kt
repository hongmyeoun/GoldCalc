package com.hongmyeoun.goldcalc.model.searchedInfo.skills

import com.google.gson.JsonParser

class CombatSkillsDetail(private val combatSkills: List<CombatSkills>) {
    fun getSkills(): List<Skills> {
        val skillList = mutableListOf<Skills>()

        for (skill in combatSkills) {

            val gemList = getSkillGem(skill)

            if (gemList != null) {
                val skills = Skills(
                    icon = skill.icon,
                    name = skill.name,
                    level = skill.level,
                    tripods = skill.tripods,
                    rune = skill.rune,
                    gem = gemList
                )
                skillList.add(skills)
            } else {
                val skills = Skills(
                    icon = skill.icon,
                    name = skill.name,
                    level = skill.level,
                    tripods = skill.tripods,
                    rune = skill.rune
                )
                skillList.add(skills)
            }
        }

        return skillList
    }

    private fun getSkillGem(combatSkills: CombatSkills): List<Map<String, String>>? {
        val tooltip = JsonParser.parseString(combatSkills.tooltip).asJsonObject

        for (index in 7..8) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltip.has(elementKey)) {
                val element = tooltip.getAsJsonObject(elementKey)
                if (element.get("type").asString == "ItemPartBox") {
                    val value = element.getAsJsonObject("value")
                    if (value.get("Element_000").asString.contains("보석 효과")) {
                        val gem = value.get("Element_001").asString

                        return getGem(gem)
                    }
                }
            }
        }

        return null
    }

    private fun getGem(str: String) : MutableList<Map<String, String>> {
        val gemList = mutableListOf<Map<String, String>>()

        val regex = "(\\d+(\\.\\d+)?)% (증가|감소)".toRegex()
        val matches = regex.findAll(str)

        for (match in matches) {
            val percent = match.groupValues[1].toDouble().toInt() // 숫자 부분을 추출하여 정수로 변환
            val type = match.groupValues[3] // "증가" 또는 "감소" 부분을 추출

            val result = if (type == "증가") {
                calcAnnLevel(percent).toString() + "멸"
            } else {
                calcCriLevel(percent).toString() + "홍"
            }

            val icon = getGemIcon(result)

            gemList.add(mapOf("name" to result, "icon" to icon))
        }

        return gemList
    }

    private fun calcAnnLevel(percent: Int): Int {
        return when {
            percent <= 24 -> percent / 3
            percent <= 30 -> 9
            else -> 10
        }
    }

    private fun calcCriLevel(percent: Int): Int {
        return percent / 2
    }

    private fun getGemIcon(gemInfo: String): String {
        val baseUrl = "https://cdn-lostark.game.onstove.com/efui_iconatlas/use/"
        val dotPNG = ".png"

        return when (gemInfo) {
            "1멸" -> baseUrl + "use_9_46" + dotPNG
            "2멸" -> baseUrl + "use_9_47" + dotPNG
            "3멸" -> baseUrl + "use_9_48" + dotPNG
            "4멸" -> baseUrl + "use_9_49" + dotPNG
            "5멸" -> baseUrl + "use_9_50" + dotPNG
            "6멸" -> baseUrl + "use_9_51" + dotPNG
            "7멸" -> baseUrl + "use_9_52" + dotPNG
            "8멸" -> baseUrl + "use_9_53" + dotPNG
            "9멸" -> baseUrl + "use_9_54" + dotPNG
            "10멸" -> baseUrl + "use_9_55" + dotPNG
            "1홍" -> baseUrl + "use_9_56" + dotPNG
            "2홍" -> baseUrl + "use_9_57" + dotPNG
            "3홍" -> baseUrl + "use_9_58" + dotPNG
            "4홍" -> baseUrl + "use_9_59" + dotPNG
            "5홍" -> baseUrl + "use_9_60" + dotPNG
            "6홍" -> baseUrl + "use_9_61" + dotPNG
            "7홍" -> baseUrl + "use_9_62" + dotPNG
            "8홍" -> baseUrl + "use_9_63" + dotPNG
            "9홍" -> baseUrl + "use_9_64" + dotPNG
            "10홍" -> baseUrl + "use_9_65" + dotPNG
            else -> ""
        }
    }
}