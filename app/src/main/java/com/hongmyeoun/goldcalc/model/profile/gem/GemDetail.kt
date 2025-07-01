package com.hongmyeoun.goldcalc.model.profile.gem

import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.COOLTIME
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.DEAL

class GemDetail(private val gemAndEffect: GemAndEffect) {
    fun getCharacterGemDetails(): List<Gem> {
        val gemMap = gemAndEffect.gems?.associateBy { it.slot }

        return gemAndEffect.effects.skills.mapNotNull { skill ->
            gemMap?.get(skill.gemSlot)?.let { gem ->
                val name = gemName(gem)
                val type = if (skill.description[0].contains(DEAL)) DEAL else COOLTIME
                Gem(
                    type = type,
                    name = name,
                    grade = gem.grade,
                    level = gem.level,
                    gemIcon = gem.icon,
                    skillIcon = skill.icon,
                    skill = skill.name,
                    effect = skill.description[0],
                    option = skill.option
                )
            }
        }
    }

    private fun gemName(gem: Gems): String {
        val name = gem.name
        val fullName = removeHTMLTags(name)
        return fullName.split(" ")[1].split("의")[0]
    }

    private fun removeHTMLTags(htmlStr: String): String {
        return htmlStr.replace(Regex("<.*?>"), "")
    }

}