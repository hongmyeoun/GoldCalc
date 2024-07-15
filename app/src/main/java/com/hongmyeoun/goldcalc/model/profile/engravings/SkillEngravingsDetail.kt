package com.hongmyeoun.goldcalc.model.profile.engravings

import com.google.gson.JsonParser
import com.hongmyeoun.goldcalc.model.constants.TooltipStrings

class SkillEngravingsDetail(private val skillEngravings: SkillEngravingsAndEffects) {
    fun getEngravingsDetail(): List<SkillEngravings> {
        if (skillEngravings.engravings.isNullOrEmpty() || skillEngravings.effect.isNullOrEmpty()) {
            return skillEngravings.arkPassiveEffect?.map { passiveEffect ->
                SkillEngravings(
                    name = passiveEffect.name,
                    level = passiveEffect.level.toString(),
                    description = passiveEffect.description,
                    abilityStoneLevel = passiveEffect.abilityStoneLevel,
                    icon = null,
                    grade = passiveEffect.grade
                )
            } ?: emptyList()
        }

        val engravingMap = skillEngravings.engravings.associateBy { it?.name }

        return skillEngravings.effect.map { effect ->
            val name = effect.name.substringBefore(TooltipStrings.SubStringBefore.SPACE_LEVEL)
            val engravingDetail = SkillEngravings(
                name = name,
                icon = effect.icon,
                level = effect.name.substringAfter(TooltipStrings.SubStringAfter.LEVEL_DOT_SPACE),
                description = removeHTMLTags(effect.description)
            )

            engravingMap[name]?.let { engraving ->
                engravingDetail.copy(
                    awakenEngravingsPoint = getAwakenEngravingsPoint(engraving)
                )
            } ?: engravingDetail
        }
    }

//    fun getEngravingsDetail(): List<SkillEngravings> {
//        val engravingMap = skillEngravings.engravings?.associateBy { it?.name }
//
//        return skillEngravings.effect?.map { effect ->
//            val name = effect.name.substringBefore(TooltipStrings.SubStringBefore.SPACE_LEVEL)
//            val engravingDetail = SkillEngravings(
//                name = name,
//                icon = effect.icon,
//                level = effect.name.substringAfter(TooltipStrings.SubStringAfter.LEVEL_DOT_SPACE),
//                description = removeHTMLTags(effect.description)
//            )
//
//            engravingMap?.get(name)?.let { engraving ->
//                engravingDetail.copy(
//                    awakenEngravingsPoint = getAwakenEngravingsPoint(engraving)
//                )
//            } ?: engravingDetail
//        } ?:
//    }

    private fun getAwakenEngravingsPoint(skillEngraving: SkillEngraving): String {
        val tooltip = JsonParser.parseString(skillEngraving.tooltip).asJsonObject

        val awakenPointStr = tooltip
            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_001)
            .getAsJsonObject(TooltipStrings.MemberName.VALUE)
            .get(TooltipStrings.MemberName.ENGRAVINGS_POINT)
            .asString

        return awakenPointStr
            .substringAfter(TooltipStrings.SubStringAfter.POINT_SPACE)
            .substringBefore(TooltipStrings.SubStringBefore.FONT_END)
    }

    // TODO: HTML TAG 지우기 공통화
    private fun removeHTMLTags(htmlStr: String): String {
        return htmlStr.replace(Regex("<.*?>"), "")
    }
}