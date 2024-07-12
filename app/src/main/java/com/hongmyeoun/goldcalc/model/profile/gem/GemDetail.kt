package com.hongmyeoun.goldcalc.model.profile.gem

class GemDetail(private val gemAndEffect: GemAndEffect) {
    fun getCharacterGemDetails(): List<Gem> {
        val gemMap = gemAndEffect.gems?.associateBy { it.slot }

        return gemAndEffect.effects.skills.mapNotNull { skill ->
            gemMap?.get(skill.gemSlot)?.let { gem ->
                val type = gemType(gem)
                Gem(
                    type = type,
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

    private fun gemType(gem: Gems): String {
        val name = gem.name
        val fullName = removeHTMLTags(name)
        return fullName.split(" ")[1].split("의")[0]
    }

    private fun removeHTMLTags(htmlStr: String): String {
        return htmlStr.replace(Regex("<.*?>"), "")
    }

}