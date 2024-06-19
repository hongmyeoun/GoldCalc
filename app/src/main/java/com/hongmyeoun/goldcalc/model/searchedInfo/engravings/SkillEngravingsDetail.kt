package com.hongmyeoun.goldcalc.model.searchedInfo.engravings

import com.google.gson.JsonParser

class SkillEngravingsDetail(private val skillEngravings: SkillEngravingsAndEffects) {
    fun getEngravingsDetail(): List<SkillEngravings> {
        val engravingMap = skillEngravings.engravings?.associateBy { it?.name }

        return skillEngravings.effect.map { effect ->
            val name = effect.name.substringBefore(" Lv")
            val engravingDetail = SkillEngravings(
                name = name,
                icon = effect.icon,
                level = effect.name.substringAfter("Lv. "),
                description = removeHTMLTags(effect.description)
            )

            engravingMap?.get(name)?.let { engraving ->
                engravingDetail.copy(
                    awakenEngravingsPoint = getAwakenEngravingsPoint(engraving)
                )
            } ?: engravingDetail
        }
    }

    private fun getAwakenEngravingsPoint(skillEngraving: SkillEngraving): String {
        val tooltip = JsonParser.parseString(skillEngraving.tooltip).asJsonObject

        val awakenPointStr = tooltip.getAsJsonObject("Element_001").getAsJsonObject("value").get("leftText").asString

        return awakenPointStr.substringAfter("포인트 ").substringBefore("</FONT>")
    }

    private fun removeHTMLTags(htmlStr: String): String {
        return htmlStr.replace(Regex("<.*?>"), "")
    }
}