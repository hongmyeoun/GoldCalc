package com.hongmyeoun.goldcalc.model.profile.arkGrid

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hongmyeoun.goldcalc.model.constants.TooltipStrings
import com.hongmyeoun.goldcalc.model.profile.Common

class ArkGridDetail(private val arkGrid: ArkGrid) {
    fun getArkGridDetail(): List<ArkGridCoreAndGemsTooltips> {
        val arkGridSlots = mutableListOf<ArkGridCoreAndGemsTooltips>()

        if (arkGrid.slots != null) {
            for (slot in arkGrid.slots) {
                val (gemBasicInfo, gemPoint, gemOption) = getGemsInfo(slot)
                val arkGridCoreAndGemsTooltips = ArkGridCoreAndGemsTooltips(
                    coreOption = getCoreOption(slot),
                    coreTrigger = getCoreTrigger(slot),
                    gemBasicInfo = gemBasicInfo,
                    gemPoint = gemPoint,
                    gemOption = gemOption
                )

                arkGridSlots.add(arkGridCoreAndGemsTooltips)
            }
        }

        return arkGridSlots
    }

    private fun getCoreType(tooltips: JsonObject): Boolean {
        return Common.safeGetAsJsonObject(tooltips, TooltipStrings.MemberName.ELEMENT_000)
            ?.get(TooltipStrings.MemberName.VALUE)
            ?.asString
            ?.contains("질서") == true
    }

    private fun getCoreOption(slot: ArkGridSlot): String {
        val tooltips = JsonParser.parseString(slot.tooltip).asJsonObject

        val isCosmos = getCoreType(tooltips)

        val coreType = if (isCosmos) TooltipStrings.MemberName.ELEMENT_006 else TooltipStrings.MemberName.ELEMENT_005

        val coreOption = Common.safeGetAsJsonObject(tooltips, coreType)
            ?.let { Common.safeGetAsJsonObject(it, TooltipStrings.MemberName.VALUE) }
            ?.get(TooltipStrings.MemberName.ELEMENT_001)
            ?.asString ?: ""

        return coreOption
    }

    private fun getCoreTrigger(slot: ArkGridSlot): String {
        val tooltips = JsonParser.parseString(slot.tooltip).asJsonObject

        val isCosmos = getCoreType(tooltips)

        return if (isCosmos) {
            Common.safeGetAsJsonObject(tooltips, TooltipStrings.MemberName.ELEMENT_007)
                ?.let { Common.safeGetAsJsonObject(it, TooltipStrings.MemberName.VALUE) }
                ?.get(TooltipStrings.MemberName.ELEMENT_001)
                ?.asString ?: ""
        } else {
            ""
        }
    }

    private fun getGemsInfo(slot: ArkGridSlot): Triple<List<String>, List<String>, List<String>> {
        val basicInfoList = mutableListOf<String>()
        val pointList = mutableListOf<String>()
        val optionList = mutableListOf<String>()

        if (slot.gems != null) {

            for (gem in slot.gems) {
                basicInfoList.add(getGemBasicInfo(gem))
                pointList.add(getGemPoint(gem))
                optionList.add(getGemOption(gem))
            }

            return Triple(basicInfoList, pointList, optionList)
        }
        return Triple(basicInfoList, pointList, optionList)
    }

    private fun getGemBasicInfo(gem: ArkGridGem): String {
        val tooltips = JsonParser.parseString(gem.tooltip).asJsonObject

        val basicInfo = Common.safeGetAsJsonObject(tooltips, TooltipStrings.MemberName.ELEMENT_004)
            ?.let { Common.safeGetAsJsonObject(it, TooltipStrings.MemberName.VALUE) }
            ?.get(TooltipStrings.MemberName.ELEMENT_001)
            ?.asString ?: ""

        return basicInfo
    }

    private fun getGemPoint(gem: ArkGridGem): String {
        val tooltips = JsonParser.parseString(gem.tooltip).asJsonObject

        val point = Common.safeGetAsJsonObject(tooltips, TooltipStrings.MemberName.ELEMENT_005)
            ?.let { Common.safeGetAsJsonObject(it, TooltipStrings.MemberName.VALUE) }
            ?.get(TooltipStrings.MemberName.ELEMENT_001)
            ?.asString ?: ""

        return point
    }

    private fun getGemOption(gem: ArkGridGem): String {
        val tooltips = JsonParser.parseString(gem.tooltip).asJsonObject

        val option = Common.safeGetAsJsonObject(tooltips, TooltipStrings.MemberName.ELEMENT_005)
            ?.let { Common.safeGetAsJsonObject(it, TooltipStrings.MemberName.VALUE) }
            ?.get(TooltipStrings.MemberName.ELEMENT_001)
            ?.asString ?: ""

        return option
    }
}