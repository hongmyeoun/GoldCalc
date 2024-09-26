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
                        setOption = getSetOption(equipment),
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
                        combatStat1 = getAccCombatStats(equipment)?.first,
                        combatStat2 = getAccCombatStats(equipment)?.second,
                        engraving1 = getAccFirstEngraving(equipment),
                        engraving2 = getAccSecondEngraving(equipment),
                        engraving3 = getAccThirdEngraving(equipment),
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
                    val (specialEffect, stats, extraStats) = getBraceletEffect(equipment)
                    val bracelet = Bracelet(
                        type = getEquipmentType(equipment),
                        grade = getEquipmentGrade(equipment),
                        name = getAccName(equipment),
                        itemIcon = getItemIcon(equipment),
                        specialEffect = specialEffect,
                        stats = stats,
                        extraStats = extraStats
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
                val element = tooltipJson.getAsJsonObject(elementKey)

                if (Common.indentStringGroup(element)) {
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
                val element = tooltipJson.getAsJsonObject(elementKey)

                if (Common.indentStringGroup(element)) {
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

    private fun getSetOption(equipment: Equipment): String {
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 7..13) {
            val elementKey = Common.currentElementKey(index)

            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson
                    .getAsJsonObject(elementKey)

                if (Common.itemPartBox(element)) {
                    val value = element
                        .getAsJsonObject(TooltipStrings.MemberName.VALUE)
                        .get(TooltipStrings.MemberName.ELEMENT_001).asString

                    if (value.contains(TooltipStrings.Contains.LEVEL_DOT)) {
                        val setOption = value
                            .substringBefore(TooltipStrings.SubStringBefore.SPACE_FONT_EMPTY)

                        val setLevel = value
                            .substringAfterLast(TooltipStrings.SubStringAfter.LEVEL_DOT)
                            .substringBefore(TooltipStrings.SubStringBefore.FONT_END)

                        return "$setOption $setLevel"
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
                val element = tooltipJson.getAsJsonObject(elementKey)

                if (Common.indentStringGroup(element)) {
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

    private fun getAccCombatStats(equipment: Equipment): Pair<String?, String?>? {
        val combatStat = combatStatElement(equipment)

        if (combatStat != null) {
            return combatStatProcess(combatStat)
        }

        return null
    }

    private fun combatStatElement(equipment: Equipment): String? {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        val itemTier = itemTier(tooltip)

        if (itemTier < 4) {
            return tooltip
                .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_005)
                .getAsJsonObject(TooltipStrings.MemberName.VALUE)
                .get(TooltipStrings.MemberName.ELEMENT_001).asString
        }

        return null
    }

    private fun getAccFirstEngraving(equipment: Equipment): String {
        return accEngraving(equipment, TooltipStrings.MemberName.ELEMENT_000)
    }

    private fun getAccSecondEngraving(equipment: Equipment): String {
        return accEngraving(equipment, TooltipStrings.MemberName.ELEMENT_001)
    }

    private fun getAccThirdEngraving(equipment: Equipment): String {
        return accEngraving(equipment, TooltipStrings.MemberName.ELEMENT_002)
    }

    private fun accEngraving(equipment: Equipment, memberName: String): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        val itemTier = itemTier(tooltip)

        if (itemTier < 4) {
            val element = tooltip
                .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_006)

            val slicingOptionAfter =
                if (memberName == TooltipStrings.MemberName.ELEMENT_002) TooltipStrings.SubStringAfter.FONT_ENGRAVING_COLOR_THIRD else TooltipStrings.SubStringAfter.FONT_ENGRAVING_COLOR

            if (Common.notSingleTextBox(element)) {
                val contentStr = tooltip
                    .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_006)
                    .getAsJsonObject(TooltipStrings.MemberName.VALUE)
                    .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_000)
                    .getAsJsonObject(TooltipStrings.MemberName.CONTENT)

                if (contentStr.has(memberName)) {
                    val engraving = contentStr
                        .getAsJsonObject(memberName)
                        .get(TooltipStrings.MemberName.CONTENT).asString

                    val option = engraving
                        .substringAfter(slicingOptionAfter)
                        .substringBefore(TooltipStrings.SubStringBefore.FONT_END)

                    val activation = engraving
                        .substringAfter(TooltipStrings.SubStringAfter.AWAKEN_PLUS)
                        .substringBefore(TooltipStrings.SubStringBefore.ENTER_HTML)

                    return "$option $activation"
                }
            }
        }
        return ""
    }

    private fun getGrindEffect(equipment: Equipment): String? {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 5..8) {
            val elementKey = Common.currentElementKey(index)

            if (tooltip.has(elementKey)) {
                val element = tooltip.getAsJsonObject(elementKey)

                if (Common.itemPartBox(element)) {
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
                        val itemTier = itemTier(tooltip)

                        if (itemTier >= 4) {
                            val rawGrinding = value
                                .get(TooltipStrings.MemberName.ELEMENT_001)
                                .asString

                            return accGrindingProcess(rawGrinding).ifEmpty { TooltipStrings.NoResult.GRIND }
                        }
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
                val element = tooltip.getAsJsonObject(elementKey)

                if (Common.itemPartBox(element)) {
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
                        val itemTier = itemTier(tooltip)

                        if (itemTier >= 4) {
                            return value
                                .get(TooltipStrings.MemberName.ELEMENT_001)
                                .asString
                        }
                    }
                }
            }
        }

        return null
    }

    private fun itemTier(tooltip: JsonObject): Int {
        val hasTier = tooltip
            .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_001)
            .getAsJsonObject(TooltipStrings.MemberName.VALUE)
            .get(TooltipStrings.MemberName.ITEM_LEVEL)
            .asString
            .contains(TooltipStrings.Contains.TIER)

        if (hasTier) {
            return tooltip
                .getAsJsonObject(TooltipStrings.MemberName.ELEMENT_001)
                .getAsJsonObject(TooltipStrings.MemberName.VALUE)
                .get(TooltipStrings.MemberName.ITEM_LEVEL)
                .asString
                .substringAfter(TooltipStrings.SubStringAfter.ITEM_TIER)
                .substringBefore(TooltipStrings.SubStringBefore.FONT_END)
                .toInt()
        }

        return 1
    }

    private fun getABStoneHPBonus(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 5..6) {
            val elementKey = Common.currentElementKey(index)

            if (tooltip.has(elementKey)) {
                val element = tooltip.getAsJsonObject(elementKey)

                if (Common.itemPartBox(element)) {
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

        for (index in 5..6) {
            val elementKey = Common.currentElementKey(index)

            if (tooltip.has(elementKey)) {
                val element = tooltip.getAsJsonObject(elementKey)

                if (Common.indentStringGroup(element)) {
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
                val element = tooltip.getAsJsonObject(elementKey)

                if (Common.itemPartBox(element)) {
                    val testValue = element
                        .get(TooltipStrings.MemberName.VALUE)

                    if (testValue == null || !testValue.isJsonObject) {
                        continue
                    }

                    val value = element.getAsJsonObject(TooltipStrings.MemberName.VALUE)

                    if (value.get(TooltipStrings.MemberName.ELEMENT_000).asString.contains(TooltipStrings.Contains.BRACELET)) {
                        val itemTier = itemTier(tooltip)
                        val effect = value.get(TooltipStrings.MemberName.ELEMENT_001).asString
                        if (itemTier >= 4) {
                            val (effects, allStats, extra) = bracelet4tierSpliter(effect)

                            return Triple(effects, allStats, extra)
                        } else {
                            val (effects, keyStats) = bracelet3tierSpliter(effect)
                            val allStats = threeTierAllStats(effect)

                            return Triple(effects, keyStats, allStats)
                        }
                    }
                }
            }
        }

        return Triple(emptyList(), emptyList(), emptyList())
    }

    private fun bracelet4tierSpliter(input: String): Triple<List<Pair<String, String>>, List<Pair<String, String>>, List<Pair<String, String>>> {
        val result = input.replace(Regex("<img[^>]*>"), "").replace("</img>", "")
        val parts = result.split(Regex("<BR>", RegexOption.IGNORE_CASE))

        val newInput = parts.map { it.trim() }.filter { it.isNotEmpty() }
        return parseAndFilter(newInput)
    }

    private fun parseAndFilter(input: List<String>): Triple<List<Pair<String, String>>, List<Pair<String, String>>, List<Pair<String, String>>> {
        val keywords = EquipmentConsts.COMBAT_STAT_LIST

        // extra은 추가 특성(힘민지, 무공, 치적 등) (key, value 쌍)
        val extra = mutableListOf<Pair<String, String>>()

        // allstats는 전투 특성(치특신제인숙) (key, value 쌍)
        val allstats = mutableListOf<Pair<String, String>>()

        // effects는 특수 효과(추가특성 +a, 완전 특수 효과 등) (key, value 쌍)
        val effects = mutableListOf<Pair<String, String>>()

        for (item in input) {
            // 조건 1: 항목이 "." 또는 ")"로 끝나는지 확인
            if (item.endsWith(".") || item.endsWith(")")) {
                effects.add(Pair("특수 효과", item)) // output3에 추가
            } else {
                // 각 아이템의 첫 단어가 키워드 리스트에 있는지 확인
                val keyword = keywords.find { item.split(" ")[0] == it } // 첫 단어가 정확히 일치해야 함

                if (keyword != null) {
                    // 매칭된 경우: "키워드"와 그 뒤의 값으로 분리
                    val value = item.removePrefix(keyword).trim() // 키워드를 제거하고 뒤의 값을 추출
                    allstats.add(Pair(keyword, value)) // 키워드와 값의 쌍을 output2에 추가
                } else {
                    // 매칭되지 않은 경우: "+" 기준으로 key와 value로 분리
                    val plusIndex = item.indexOf("+")
                    if (plusIndex != -1) {
                        val key = item.substring(0, plusIndex).trim() // "+" 앞 부분을 key로 설정
                        val value = item.substring(plusIndex).trim() // "+"부터 끝까지를 value로 설정
                        extra.add(Pair(key, value))
                    } else {
                        // "+"가 없을 경우 해당 항목을 처리하지 않음 (혹은 처리 방식에 따라 달라질 수 있음)
                        extra.add(Pair(item, "")) // "+"가 없는 경우 value는 빈 값으로 처리
                    }
                }
            }
        }

        return Triple(effects, allstats, allstats + extra) // 세 개의 결과를 반환
    }

    private fun bracelet3tierSpliter(input: String): Pair<List<Pair<String, String>>, List<Pair<String, String>>> {
        // <BR> 태그를 줄 나누기로 변환
        var withLineBreaks = input.replace(Regex("(?i)<BR>(?=[<\\[])"), "\n<BR>")

        // <FONT COLOR='#...'>(...)</FONT> 형식의 태그를 남기되, color 속성 값이 빈 값인 태그는 제거
        withLineBreaks = withLineBreaks.replace(Regex("<FONT COLOR='\\s*'>"), "")

        // [이름] 값 패턴 찾아서 key와 값을 분리하여 리스트에 저장
        val namePattern = Regex("\\[([^\\[\\]]+)\\]\\s*(.+)")
        val nameValueList = mutableListOf<Pair<String, String>>()

        withLineBreaks.split("\n").forEach { line ->
            namePattern.find(line)?.let { matchResult ->
                // Clean up <FONT COLOR='...'> and </FONT> tags
                val key = matchResult.groupValues[1]
                    .replace(Regex("<FONT\\s+COLOR='[^']*'>"), "") // Removes <FONT COLOR='...'>
                    .replace("</FONT>", "") // Removes </FONT>
                    .replace("\\s+".toRegex(), "") // Removes extra spaces

                val value = matchResult.groupValues[2]
                    .replace("<BR>", "\n\n").trim()

                nameValueList.add(key to value)
            }
        }

        // <BR> 태그를 줄 나누기로 변환
        var withLineBreaks2 = input.replace(Regex("(?i)<BR>(?=[<\\[])"), "\n<BR>")

        // HTML 태그 제거
        withLineBreaks2 = withLineBreaks2.replace(Regex("<[^>]*>"), "")

        // 키워드 패턴과 값 찾아서 리스트에 저장
        val keywords = EquipmentConsts.FILTERED_COMBAT_STAT_LIST
        val keyValuePattern = Regex("(\\S+)\\s*\\+\\s*(.+)")
        val keyValueList = mutableListOf<Pair<String, String>>()

        withLineBreaks2.split("\n").forEach { line ->
            keyValuePattern.find(line)?.let { matchResult ->
                val key = matchResult.groupValues[1]
                val value = matchResult.groupValues[2].trim()
                if (keywords.contains(key)) {
                    keyValueList.add(key to value)
                }
            }
        }

        // 결과 반환
        return nameValueList to keyValueList
    }

    private fun threeTierAllStats(input: String): List<Pair<String, String>> {
        // <BR> 태그를 줄 나누기로 변환
        var withLineBreaks = input.replace(Regex("(?i)<BR>(?=[<\\[])"), "\n<BR>")

        // HTML 태그 제거
        withLineBreaks = withLineBreaks.replace(Regex("<[^>]*>"), "")

        // 키 + 값 패턴 찾아서 key와 값을 분리하여 리스트에 저장
        val keyValuePattern = Regex("(\\S+)\\s*\\+\\s*(.+)")
        val keyValueList = mutableListOf<Pair<String, String>>()

        withLineBreaks.split("\n").forEach { line ->
            keyValuePattern.find(line)?.let { matchResult ->
                val key = matchResult.groupValues[1]
                val value = matchResult.groupValues[2].trim()
                keyValueList.add(key to value)
            }
        }

        // 결과 반환
        return keyValueList
    }

    private fun accGrindingProcess(input: String): String {
        return input.replace(Regex("<img[^>]*>"), "") // Remove <img> tags
            .replace("</img>", "")           // Remove </img> tags
            .replace("<BR>", "\n")           // Replace <BR> with newline
            .replace("&nbsp;", " ")          // Replace non-breaking space
            .trim()                          // Trim leading/trailing whitespace
    }

    private fun combatStatProcess(input: String): Pair<String?, String?> {
        val cleanedInput = input.replace(Regex("<FONT COLOR[^>]*>"), "")
            .replace("</FONT>", "")
            .replace("<BR>", "\n")
            .replace("&nbsp;", " ")
            .trim()

        val parts = cleanedInput.split("\n")
        val firstPart = parts.getOrNull(0)
        val secondPart = parts.getOrNull(1)

        return Pair(firstPart, secondPart)
    }

    private fun testAbilityStone(input: String): Pair<String, Int?> {
        val pattern = """\[<FONT COLOR='#(?:787878|FFFFFF|FFFFAC|FE2E2E)'>\s*([^<]+)\s*<\/FONT>\]\s*활성도\s*(\d+)""".toRegex()
        val matchResult = pattern.find(input)

        if (matchResult != null) {
            val ability = matchResult.groups[1]?.value ?: ""
            val level = matchResult.groups[2]?.value?.toIntOrNull()

            return ability to level
        }

        return "" to null
    }
}


