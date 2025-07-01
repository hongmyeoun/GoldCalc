package com.hongmyeoun.goldcalc.model.profile.skills

import com.google.gson.JsonParser
import com.hongmyeoun.goldcalc.model.constants.TooltipStrings
import com.hongmyeoun.goldcalc.model.profile.Common

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
            val elementKey = Common.currentElementKey(index)
            if (tooltip.has(elementKey)) {
                val element = tooltip.getAsJsonObject(elementKey)
                if (Common.itemPartBox(element)) {
                    val value = element
                        .getAsJsonObject(TooltipStrings.MemberName.VALUE)

                    if (value.get(TooltipStrings.MemberName.ELEMENT_000).asString.contains(TooltipStrings.Contains.GEM_EFFECTS)) {
                        return true
                    }
                }
            }
        }

        return false
    }

    private fun getRuneTooltip(rune: Rune): String? {
        val tooltip = JsonParser.parseString(rune.tooltip).asJsonObject

        return tooltip
            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_003)
            .getAsJsonObject(TooltipStrings.MemberName.VALUE)
            .get(TooltipStrings.MemberName.ELEMENT_001).asString
    }

}