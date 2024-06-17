package com.hongmyeoun.goldcalc.model.searchedInfo.skills

import com.google.gson.JsonParser

class CombatSkillsDetail(private val combatSkills: List<CombatSkills>) {
    fun getSkills(): List<Skills> {
        val skillList = mutableListOf<Skills>()

        for (skill in combatSkills) {

            val gemList = getSkillGem(skill)

            if (skill.rune != null) {
                val skills = Skills(
                    icon = skill.icon,
                    name = skill.name,
                    level = skill.level,
                    tripods = skill.tripods,
                    rune = skill.rune,
                    gem = gemList,
                    runeTooltip = getRuneTooltip(skill.rune)
                )
                skillList.add(skills)
            } else {
                val skills = Skills(
                    icon = skill.icon,
                    name = skill.name,
                    level = skill.level,
                    tripods = skill.tripods,
                    gem = gemList
                )
                skillList.add(skills)
            }

        }

        return skillList
    }

    private fun getSkillGem(combatSkills: CombatSkills): Boolean {
        val tooltip = JsonParser.parseString(combatSkills.tooltip).asJsonObject

        for (index in 7..8) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltip.has(elementKey)) {
                val element = tooltip.getAsJsonObject(elementKey)
                if (element.get("type").asString == "ItemPartBox") {
                    val value = element.getAsJsonObject("value")
                    if (value.get("Element_000").asString.contains("보석 효과")) {
                        return true
                    }
                }
            }
        }

        return false
    }

    private fun getRuneTooltip(rune: Rune): String? {
        val tooltip = JsonParser.parseString(rune.tooltip).asJsonObject

        return tooltip.getAsJsonObject("Element_002").getAsJsonObject("value").get("Element_001").asString
    }

}