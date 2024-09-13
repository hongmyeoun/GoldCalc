package com.hongmyeoun.goldcalc.model.profile.arkpassive

import com.google.gson.JsonParser
import com.hongmyeoun.goldcalc.model.constants.TooltipStrings

class ArkPassiveDetail(private val arkPassive: ArkPassive) {
    fun getArkPassiveDetail(): List<ArkPassiveNode> {
        val arkPassives = mutableListOf<ArkPassiveNode>()

        for (arkPassiveNode in arkPassive.effects) {
            val (type, tier, level) = splitDescription(arkPassiveNode.description)

            val arkPassiveNodeDetail = ArkPassiveNode(
                type = type,
                tier = tier ,
                name = getName(arkPassiveNode),
                level = level,
                icon = arkPassiveNode.icon,
                script = getScript(arkPassiveNode),
            )

            arkPassives.add(arkPassiveNodeDetail)
        }

        return arkPassives
    }

    private fun splitDescription(description: String): Triple<String, String, String> {
        val removedScripts = removeHTMLTags(description).split(' ')
        val type = removedScripts[0]
        val tier = removedScripts[1]
        val level = removedScripts.last()

        return Triple(type, tier, level)
    }

    private fun getName(arkPassiveSkillEffects: ArkPassiveSkillEffects): String {
        val tooltip = JsonParser.parseString(arkPassiveSkillEffects.tooltip).asJsonObject

        return tooltip.getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000).get(TooltipStrings.MemberName.VALUE).asString
    }

    private fun getScript(arkPassiveSkillEffects: ArkPassiveSkillEffects): String {
        val tooltip = JsonParser.parseString(arkPassiveSkillEffects.tooltip).asJsonObject

        return tooltip.getAsJsonObject(TooltipStrings.MemberName.ELEMENT_002).get(TooltipStrings.MemberName.VALUE).asString
    }


    private fun removeHTMLTags(htmlStr: String): String {
        return htmlStr.replace(Regex("<.*?>"), "")
    }
}