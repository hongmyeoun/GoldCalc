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

    private fun getSkillGem(combatSkills: CombatSkills): List<String>? {
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

    private fun getGem(str: String) : List<String> {
        val gemList = mutableListOf<String>()

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

            gemList.add(result)
        }

        return gemList
    }


    private fun getGemLevel(gemType: String, percent: Int): Int {
        return if (gemType == "멸") {
            calcAnnLevel(percent)
        } else {
            calcCriLevel(percent)
        }
    }

    private fun calcAnnLevel(percent: Int): Int {
        val level = if (percent <= 24) {
            percent / 3
        } else if (percent <= 30) {
            9
        } else {
            10
        }

        return level
    }

    private fun calcCriLevel(percent: Int): Int {
        return percent / 2
    }
}