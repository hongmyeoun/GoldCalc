package com.hongmyeoun.goldcalc.model.searchedInfo.gem

import com.google.gson.JsonObject
import com.google.gson.JsonParser

class GemDetail(private val gems: List<Gems>) {
    private fun parserGemsTooltip(gem: Gems) : JsonObject {
        return JsonParser.parseString(gem.tooltip).asJsonObject
    }

    fun getCharacterGemDetails(): List<Gem> {
        val gemList = mutableListOf<Gem>()

        for (gem in gems) {
            val (type, skill, effect) = getGemType(gem)
            val gemDetails = Gem(
                type = type,
                level = gem.level,
                icon = gem.icon,
                skill = skill,
                effect = effect,
            )
            gemList.add(gemDetails)
        }

        return gemList
    }

    private fun getGemType(gem: Gems): Triple<String, String, String> {
        val tooltip = parserGemsTooltip(gem)
        val typeStr = tooltip.getAsJsonObject("Element_004").getAsJsonObject("value").get("Element_001").asString

        val type = if (typeStr.trim().split(" ").last() == "증가") "멸화" else "홍염"
        val skill = typeStr.substringAfter("<FONT COLOR='#FFD200'>").substringBefore("</FONT>")
        val effect = typeStr.substringAfter("</FONT> ")

        return Triple(type, skill, effect)
    }
}