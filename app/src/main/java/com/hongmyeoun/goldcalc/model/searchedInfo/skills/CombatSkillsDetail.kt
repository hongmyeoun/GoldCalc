package com.hongmyeoun.goldcalc.model.searchedInfo.skills

class CombatSkillsDetail(private val combatSkills: List<CombatSkills>) {
    fun getSkills(): List<Skills> {
        val skillList = mutableListOf<Skills>()

        for (skill in combatSkills) {
            val skills = Skills(
                icon = skill.icon,
                name = skill.name,
                level = skill.level,
                tripods = skill.tripods,
                rune = skill.rune
            )
            skillList.add(skills)
        }

        return skillList
    }
}