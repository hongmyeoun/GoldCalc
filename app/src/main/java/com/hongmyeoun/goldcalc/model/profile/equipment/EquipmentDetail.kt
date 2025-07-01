package com.hongmyeoun.goldcalc.model.profile.equipment

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hongmyeoun.goldcalc.model.constants.TooltipStrings
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.model.profile.Common

class EquipmentDetail(private val equipments: List<Equipment>) {
    private fun parserEquipmentTooltip(equipment: Equipment): JsonObject {
        return JsonParser.parseString(equipment.tooltip).asJsonObject
    }

    fun getCharacterEquipmentDetails(): List<CharacterItem> {
        val characterEquipmentList = mutableListOf<CharacterItem>()

        for (equipment in equipments) {
            when (equipment.type) {
                in EquipmentConsts.EQUIPMENT_LIST -> {
                    val (elixir1Lv, elixir1Op) = getElixirFirstOptionAndLevel(equipment)
                    val (elixir2Lv, elixir2Op) = getElixirSecondOptionAndLevel(equipment)

                    val characterEquipment = CharacterEquipment(
                        type = getEquipmentType(equipment),
                        grade = getEquipmentGrade(equipment),
                        upgradeLevel = getEquipmentUpgradeLevel(equipment),
                        itemLevel = getItemLevel(equipment),
                        itemQuality = getItemQuality(equipment),
                        itemIcon = getItemIcon(equipment),
                        elixirFirstLevel = elixir1Lv,
                        elixirFirstOption = elixir1Op,
                        elixirSecondLevel = elixir2Lv,
                        elixirSecondOption = elixir2Op,
                        transcendenceLevel = getTranscendenceLevel(equipment),
                        transcendenceTotal = getTranscendenceTotal(equipment),
                        highUpgradeLevel = getHigherUpgradeLevel(equipment),
                        elixirSetOption = getElixirSetOption(equipment)
                    )
                    characterEquipmentList.add(characterEquipment)
                }

                in EquipmentConsts.ACCESSORY_LIST -> {
                    val characterAccessory = CharacterAccessory(
                        type = getEquipmentType(equipment),
                        grade = getEquipmentGrade(equipment),
                        name = getAccName(equipment),
                        itemQuality = getItemQuality(equipment),
                        itemIcon = getItemIcon(equipment),
                        grindEffect = getGrindEffect(equipment),
                        arkPassivePoint = getArkPassivePoint(equipment)
                    )
                    characterEquipmentList.add(characterAccessory)
                }

                EquipmentConsts.ABILITY_STONE -> {
                    val (eng1Op, eng1Lv) = getABStoneFirstEngraving(equipment)
                    val (eng2Op, eng2Lv) = getABStoneSecondEngraving(equipment)
                    val (eng3Op, eng3Lv) = getABStoneThirdEngraving(equipment)

                    val abilityStone = AbilityStone(
                        type = getEquipmentType(equipment),
                        grade = getEquipmentGrade(equipment),
                        name = getAccName(equipment),
                        itemIcon = getItemIcon(equipment),
                        hpBonus = getABStoneHPBonus(equipment),
                        engraving1Lv = eng1Lv,
                        engraving1Op = eng1Op,
                        engraving2Lv = eng2Lv,
                        engraving2Op = eng2Op,
                        engraving3Lv = eng3Lv,
                        engraving3Op = eng3Op,
                    )
                    characterEquipmentList.add(abilityStone)
                }

                EquipmentConsts.BRACELET -> {
                    val (basic, combat, special) = getBraceletEffect(equipment)
                    val bracelet = Bracelet(
                        type = getEquipmentType(equipment),
                        grade = getEquipmentGrade(equipment),
                        name = getAccName(equipment),
                        itemIcon = getItemIcon(equipment),
                        combat = combat,
                        basic = basic,
                        special = special,
                    )
                    characterEquipmentList.add(bracelet)
                }
            }
        }

        return characterEquipmentList
    }

    private fun getEquipmentType(equipment: Equipment): String {
        return equipment.type
    }

    private fun getEquipmentGrade(equipment: Equipment): String {
        return equipment.grade
    }

    private fun getEquipmentUpgradeLevel(equipment: Equipment): String {
        return equipment.name.split(TooltipStrings.Split.SPACE)[0]
    }

    private fun getItemLevel(equipment: Equipment): String {
        val tooltip = parserEquipmentTooltip(equipment)
        val itemTitleValue = tooltip
            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_001)
            .getAsJsonObject(TooltipStrings.MemberName.VALUE)

        return itemTitleValue
            .get(TooltipStrings.MemberName.ITEM_LEVEL)
            .asString
            .split(TooltipStrings.Split.SPACE)[3]
    }

    private fun getItemQuality(equipment: Equipment): Int {
        val tooltip = parserEquipmentTooltip(equipment)
        val itemTitleValue = tooltip
            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_001)
            .getAsJsonObject(TooltipStrings.MemberName.VALUE)

        return itemTitleValue
            .get(TooltipStrings.MemberName.QUALITY).asInt
    }

    private fun getItemIcon(equipment: Equipment): String {
        return equipment.icon
    }

    private fun getElixirFirstOptionAndLevel(equipment: Equipment): Pair<String, String> {
        val contentStr = elixirContentSTR(equipment)

        contentStr?.let {
            val firstOption = contentStr
                .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000)
                .get(TooltipStrings.MemberName.CONTENT).asString

            val option = firstOption
                .substringAfter(TooltipStrings.SubStringAfter.FONT_END_SPACE)
                .substringBefore(TooltipStrings.SubStringBefore.SPACE_FONT_ELIXIR_COLOR_LEVEL)

            val level = firstOption
                .substringAfter(TooltipStrings.SubStringAfter.FONT_START_ELIXIR_COLOR_LEVEL_DOT)
                .substringBeforeLast(TooltipStrings.SubStringBefore.FONT_END)

            return Pair(level, option)
        }

        return Pair("", "")
    }

    private fun getElixirSecondOptionAndLevel(equipment: Equipment): Pair<String, String> {
        val contentStr = elixirContentSTR(equipment)

        if (contentStr != null && contentStr.has(TooltipStrings.MemberName.ELEMENT_001)) {
            val secondOption = contentStr
                .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_001)
                .get(TooltipStrings.MemberName.CONTENT).asString

            val option = secondOption
                .substringAfter(TooltipStrings.SubStringAfter.FONT_END_SPACE)
                .substringBefore(TooltipStrings.SubStringBefore.SPACE_FONT_ELIXIR_COLOR_LEVEL)

            val level = secondOption
                .substringAfter(TooltipStrings.SubStringAfter.FONT_START_ELIXIR_COLOR_LEVEL_DOT)
                .substringBeforeLast(TooltipStrings.SubStringBefore.FONT_END)

            return Pair(level, option)
        }

        return Pair("", "")
    }

    private fun elixirContentSTR(equipment: Equipment): JsonObject? {
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 8..12) {
            val elementKey = Common.currentElementKey(index)

            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson[elementKey]?.takeIf { it.isJsonObject }?.asJsonObject

                if (element != null && Common.indentStringGroup(element)) {
                    val testValue = element.get(TooltipStrings.MemberName.VALUE)

                    if (testValue == null || !testValue.isJsonObject) {
                        continue
                    }

                    val value = element.getAsJsonObject(TooltipStrings.MemberName.VALUE)

                    if (Common.has000(value)) {
                        val topStr = value
                            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000)
                            .get(TooltipStrings.MemberName.TOP).asString

                        if (topStr.contains(TooltipStrings.Contains.ELIXIR)) {

                            return value
                                .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000)
                                .getAsJsonObject(TooltipStrings.MemberName.CONTENT)
                        }
                    }
                }
            }
        }
        return null
    }

    private fun getTranscendenceLevel(equipment: Equipment): String {
        val topStr = transcendenceTopSTR(equipment)

        if (topStr.isNotEmpty()) {
            return topStr
                .substringAfterLast(TooltipStrings.SubStringAfter.FONT_TRANSCENDENCE_COLOR)
                .substringBeforeLast(TooltipStrings.SubStringBefore.FONT_END)
        }

        return ""
    }

    private fun getTranscendenceTotal(equipment: Equipment): String {
        val topStr = transcendenceTopSTR(equipment)

        if (topStr.isNotEmpty()) {
            return topStr
                .substringAfterLast(TooltipStrings.SubStringAfter.IMG_END)
        }

        return ""
    }

    private fun transcendenceTopSTR(equipment: Equipment): String {
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 7..10) {
            val elementKey = Common.currentElementKey(index)

            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson[elementKey]?.takeIf { it.isJsonObject }?.asJsonObject

                if (element != null && Common.indentStringGroup(element)) {
                    val testValue = element.get(TooltipStrings.MemberName.VALUE)

                    if (testValue == null || !testValue.isJsonObject) {
                        continue
                    }

                    val value = element
                        .getAsJsonObject(TooltipStrings.MemberName.VALUE)

                    if (Common.has000(value)) {
                        val topStr = value
                            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000)
                            .get(TooltipStrings.MemberName.TOP).asString

                        if (topStr.contains(TooltipStrings.Contains.TRANSCENDENCE)) {
                            return topStr
                        }
                    }
                }
            }
        }

        return ""
    }

    private fun getElixirSetOption(equipment: Equipment): String {
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 9..12) {
            val elementKey = Common.currentElementKey(index)

            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson[elementKey]?.takeIf { it.isJsonObject }?.asJsonObject

                if (element != null && Common.indentStringGroup(element)) {
                    val testValue = element.get(TooltipStrings.MemberName.VALUE)

                    if (testValue == null || !testValue.isJsonObject) {
                        continue
                    }

                    val value = element
                        .getAsJsonObject(TooltipStrings.MemberName.VALUE)

                    if (Common.has000(value)) {
                        val topStr = value
                            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000)
                            .get(TooltipStrings.MemberName.TOP).asString

                        if (topStr.contains(TooltipStrings.Contains.ELIXIR_ADDITIONAL_EFFECT)) {
                            val setOption = topStr
                                .substringAfterLast(TooltipStrings.SubStringAfter.UPPER_DOT_DECREASE)
                                .substringBeforeLast(TooltipStrings.SubStringBefore.FONT_END)

                            return if (setOption.contains(TooltipStrings.Contains.STEP)) setOption else TooltipStrings.NoResult.ELIXIR_SET
                        }
                    }
                }
            }
        }

        return TooltipStrings.NoResult.ELIXIR_SET
    }

    private fun getHigherUpgradeLevel(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val element = tooltip
            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_005)

        if (Common.singleTextBox(element)) {
            val value = element
                .get(TooltipStrings.MemberName.VALUE).asString

            return value
                .substringAfterLast(TooltipStrings.SubStringAfter.FONT_TRANSCENDENCE_COLOR)
                .substringBefore(TooltipStrings.SubStringBefore.FONT_END)
        }

        return ""
    }

    private fun getAccName(equipment: Equipment): String {
        return equipment.name
    }

    private fun getGrindEffect(equipment: Equipment): String? {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 5..8) {
            val elementKey = Common.currentElementKey(index)

            if (tooltip.has(elementKey)) {
                val element = tooltip[elementKey]?.takeIf { it.isJsonObject }?.asJsonObject

                if (element != null && Common.itemPartBox(element)) {
                    val testValue = element.get(TooltipStrings.MemberName.VALUE)

                    if (testValue == null || !testValue.isJsonObject) {
                        continue
                    }

                    val value = element.getAsJsonObject(TooltipStrings.MemberName.VALUE)

                    val isGrind = value
                        .get(TooltipStrings.MemberName.ELEMENT_000)
                        .asString
                        .contains(TooltipStrings.Contains.GRIND)

                    if (isGrind) {
                        val rawGrinding = value
                            .get(TooltipStrings.MemberName.ELEMENT_001)
                            .asString

                        return accGrindingProcess(rawGrinding).ifEmpty { TooltipStrings.NoResult.GRIND }
                    }
                }
            }
        }

        return null
    }

    private fun getArkPassivePoint(equipment: Equipment): String? {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 5..8) {
            val elementKey = Common.currentElementKey(index)

            if (tooltip.has(elementKey)) {
                val element = tooltip[elementKey]?.takeIf { it.isJsonObject }?.asJsonObject

                if (element != null && Common.itemPartBox(element)) {
                    val testValue = element.get(TooltipStrings.MemberName.VALUE)

                    if (testValue == null || !testValue.isJsonObject) {
                        continue
                    }

                    val value = element.getAsJsonObject(TooltipStrings.MemberName.VALUE)

                    val isGrind = value
                        .get(TooltipStrings.MemberName.ELEMENT_000)
                        .asString
                        .contains(TooltipStrings.Contains.ARK_PASSIVE_POINT)

                    if (isGrind) {
                        return value
                            .get(TooltipStrings.MemberName.ELEMENT_001)
                            .asString
                    }
                }
            }
        }

        return null
    }

    private fun getABStoneHPBonus(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 5..6) {
            val elementKey = Common.currentElementKey(index)

            if (tooltip.has(elementKey)) {
                val element = tooltip[elementKey]?.takeIf { it.isJsonObject }?.asJsonObject

                if (element != null && Common.itemPartBox(element)) {
                    val value = element
                        .getAsJsonObject(TooltipStrings.MemberName.VALUE)

                    val bonusText = value
                        .get(TooltipStrings.MemberName.ELEMENT_000).asString

                    if (bonusText.contains(TooltipStrings.Contains.ABILITY_STONE_BONUS)) {
                        return value.get(TooltipStrings.MemberName.ELEMENT_001).asString
                    }
                }
            }
        }

        return ""
    }

    private fun getABStoneFirstEngraving(equipment: Equipment): Pair<String, Int?> {
        val contentStr = abStoneEngravingContentSTR(equipment, TooltipStrings.MemberName.ELEMENT_000)

        if (contentStr.isNotEmpty()) {

            return testAbilityStone(contentStr)
        }

        return Pair("", null)
    }

    private fun getABStoneSecondEngraving(equipment: Equipment): Pair<String, Int?> {
        val contentStr = abStoneEngravingContentSTR(equipment, TooltipStrings.MemberName.ELEMENT_001)

        if (contentStr.isNotEmpty()) {

            return testAbilityStone(contentStr)
        }

        return Pair("", null)
    }

    private fun getABStoneThirdEngraving(equipment: Equipment): Pair<String, Int?> {
        val contentStr = abStoneEngravingContentSTR(equipment, TooltipStrings.MemberName.ELEMENT_002)

        if (contentStr.isNotEmpty()) {

            return testAbilityStone(contentStr)
        }

        return Pair("", null)
    }

    private fun abStoneEngravingContentSTR(equipment: Equipment, memberName: String): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

//        val value = tooltip.getAsJsonObject(TooltipStrings.MemberName.ELEMENT_007)
//
//        val topStr = value
//            .getAsJsonObject(TooltipStrings.MemberName.VALUE)
//            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000)
//            .get(TooltipStrings.MemberName.TOP).asString
//
//        if (topStr.contains(TooltipStrings.Contains.ABILITY_STONE_ENGRAVING)) {
//
//            return value
//                .getAsJsonObject(TooltipStrings.MemberName.VALUE)
//                .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000)
//                .getAsJsonObject(TooltipStrings.MemberName.CONTENT)
//                .getAsJsonObject(memberName)
//                .get(TooltipStrings.MemberName.CONTENT).asString
//        }

        for (index in 5..7) {
            val elementKey = Common.currentElementKey(index)

            if (tooltip.has(elementKey)) {
                val element = tooltip[elementKey]?.takeIf { it.isJsonObject }?.asJsonObject

                if (element != null && Common.indentStringGroup(element)) {
                    val testValue = element
                        .get(TooltipStrings.MemberName.VALUE)

                    if (testValue == null || !testValue.isJsonObject) {
                        continue
                    }

                    val topStr = element
                        .getAsJsonObject(TooltipStrings.MemberName.VALUE)
                        .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000)
                        .get(TooltipStrings.MemberName.TOP).asString

                    if (topStr.contains(TooltipStrings.Contains.ABILITY_STONE_ENGRAVING)) {

                        return element
                            .getAsJsonObject(TooltipStrings.MemberName.VALUE)
                            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000)
                            .getAsJsonObject(TooltipStrings.MemberName.CONTENT)
                            .getAsJsonObject(memberName)
                            .get(TooltipStrings.MemberName.CONTENT).asString
                    }
                }
            }
        }

        return ""
    }

    private fun getBraceletEffect(equipment: Equipment): Triple<List<Pair<String, String>>, List<Pair<String, String>>, List<Pair<String, String>>> {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 4..5) {
            val elementKey = Common.currentElementKey(index)

            if (tooltip.has(elementKey)) {
                val element = tooltip[elementKey]?.takeIf { it.isJsonObject }?.asJsonObject

                if (element != null && Common.itemPartBox(element)) {
                    val testValue = element
                        .get(TooltipStrings.MemberName.VALUE)

                    if (testValue == null || !testValue.isJsonObject) {
                        continue
                    }

                    val value = element.getAsJsonObject(TooltipStrings.MemberName.VALUE)

                    if (value.get(TooltipStrings.MemberName.ELEMENT_000).asString.contains(TooltipStrings.Contains.BRACELET)) {
                        val effect = value.get(TooltipStrings.MemberName.ELEMENT_001).asString
                        val (basic, combat, special) = braceletSpliter(effect)

                        return Triple(basic, combat, special)
                    }
                }
            }
        }

        return Triple(emptyList(), emptyList(), emptyList())
    }

    private fun braceletSpliter(input: String): Triple<List<Pair<String, String>>, List<Pair<String, String>>, List<Pair<String, String>>> {
        val newInput = input
            .replace(
                Regex("(?i)<br>\\s*<img\\s+src=['\"]emoticon_tooltip_bracelet_locked['\"][^>]*></img> "),
                "\n"
            )
            .replace(
                Regex("(?i)<br>\\s*<img\\s+src=['\"]emoticon_tooltip_bracelet_changeable['\"][^>]*></img>"),
                "\n"
            )
            .replace(
                Regex("<img\\s+src=['\"]emoticon_tooltip_bracelet_locked['\"][^>]*></img> "),
                ""
            )

        return parseAndFilter(newInput)
    }

    private fun parseAndFilter(input: String): Triple<List<Pair<String, String>>, List<Pair<String, String>>, List<Pair<String, String>>> {
        val basicStats = setOf("힘", "민첩", "지능", "체력")
        val combatStats = setOf("치명", "특화", "신속", "제압", "숙련", "인내")

        val basic = mutableListOf<Pair<String, String>>()
        val combat = mutableListOf<Pair<String, String>>()
        val special = mutableListOf<Pair<String, String>>()

        // 정규식: [<FONT COLOR='#XXXXXX'>이름</FONT>]
        val tagPattern = Regex("""\[(<FONT\s+COLOR=['"]?#?[A-Fa-f0-9]{6}['"]?>.*?</FONT>)]""")

        input.split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .forEach { line ->
                val firstWord = line.substringBefore(" ").trim()

                when {
                    basicStats.contains(firstWord) ->
                        basic += firstWord to line.removePrefix(firstWord).trim()

                    combatStats.contains(firstWord) ->
                        combat += firstWord to line.removePrefix(firstWord).trim()

                    else -> {
                        // 특수효과 처리
                        val tagMatch = tagPattern.find(line)
                        if (tagMatch != null) {
                            val tag = tagMatch.groupValues[1]
                            val description = line.removePrefix(tagMatch.value).trim()
                            special += tag to description
                        } else {
                            special += "특수 효과" to line
                        }
                    }
                }
            }

        return Triple(basic, combat, special)
    }

    private fun accGrindingProcess(input: String): String {
        val regex = Regex(
            ">([^<]+?)<FONT COLOR=['\"]?#?([A-Fa-f0-9]{6})['\"]?>([+\\d\\.\\d%]+)</FONT>",
            RegexOption.IGNORE_CASE
        )

        return regex.findAll(input)
            .map { match ->
                val name = match.groupValues[1].trim()
                val value = match.groupValues[3]

                "$name $value"
            }
            .joinToString("\n")
    }

    private fun testAbilityStone(input: String): Pair<String, Int?> {
        val pattern =
            """<FONT COLOR='#(?:787878|FFFFFF|FFFFAC|FE2E2E)'>\[<FONT COLOR='#(?:787878|FFFFFF|FFFFAC|FE2E2E)'>\s*([^<]+)\s*<\/FONT>\]\s*<img.*?>Lv\.(\d+)<\/FONT>""".toRegex()
        val matchResult = pattern.find(input)

        if (matchResult != null) {
            val ability = matchResult.groups[1]?.value ?: ""
            val level = matchResult.groups[2]?.value?.toIntOrNull()

            return ability to level
        }

        return "" to null
    }
}


