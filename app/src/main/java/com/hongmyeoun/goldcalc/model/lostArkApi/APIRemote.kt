package com.hongmyeoun.goldcalc.model.lostArkApi

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.searchedInfo.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.Bracelet
import com.hongmyeoun.goldcalc.model.searchedInfo.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.CharacterItem
import com.hongmyeoun.goldcalc.model.searchedInfo.Equipment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object APIRemote {
    suspend fun getCharacter(context: Context, characterName: String): Pair<List<CharacterInfo>?, String?> {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

            try {
                val response = lostArkApiService.getCharacters(characterName).execute()
                if (response.isSuccessful) {
                    val characterInfoList = response.body()
                    if (characterInfoList != null) {
                        val primaryCharacter = characterInfoList.find { it.characterName == characterName }
                        val sortedCharacterInfoList = characterInfoList
                            .filter { it.characterName != characterName }
                            .sortedByDescending { it.itemMaxLevel }
                            .toMutableList()
                        primaryCharacter?.let { sortedCharacterInfoList.add(0, it) }

                        Pair(sortedCharacterInfoList, null)
                    } else {
                        Pair(null, "\"${characterName}\"(은)는 없는 결과입니다.")
                    }
                } else {
                    // 실패 처리
                    Pair(null, "서버 응답 실패: ${response.code()}")
                }
            } catch (e: IOException) {
                // 네트워크 오류 처리
                Pair(null, "네트워크 오류")
            }
        }
    }

    suspend fun getCharDetail(context: Context, characterName: String): CharacterDetail? {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

            try {
                val response = lostArkApiService.getCharacterDetail(characterName).execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    suspend fun getCharEquipment(context: Context, characterName: String): List<CharacterItem>? {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

            try {
                val response = lostArkApiService.getCharacterEquipment(characterName).execute()
                if (response.isSuccessful) {
                    val equipmentList = response.body()
                    equipmentList?.let {
                        val characterEquipmentDetail = CharacterEquipmentDetail(it)
                        return@withContext characterEquipmentDetail.getCharacterEquipmentDetails()
                    }
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    private fun authorizationHeader(context: Context): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", ContextCompat.getString(context, R.string.lost_ark_api))
                .build()
            chain.proceed(request)
        }
        return httpClient.build()
    }
}

class CharacterEquipmentDetail(private val equipments: List<Equipment>) {
    private fun parserEquipmentTooltip(equipment: Equipment): JsonObject {
        return JsonParser.parseString(equipment.tooltip).asJsonObject
    }

    fun getCharacterEquipmentDetails(): List<CharacterItem> {
        val characterEquipmentList = mutableListOf<CharacterItem>()

        for (equipment in equipments) {
            when (equipment.type) {
                "무기", "투구", "상의", "하의", "장갑", "어깨" -> {
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
                        setOption = getSetOption(equipment)
                    )
                    characterEquipmentList.add(characterEquipment)
                }

                "목걸이", "귀걸이", "반지" -> {
                    val characterAccessory = CharacterAccessory(
                        type = getEquipmentType(equipment),
                        grade = getEquipmentGrade(equipment),
                        name = getAccName(equipment),
                        itemQuality = getItemQuality(equipment),
                        itemIcon = getItemIcon(equipment),
                        combatStat1 = getAccFirstCombatStat(equipment),
                        combatStat2 = getAccSecondCombatStat(equipment),
                        engraving1 = getAccFirstEngraving(equipment),
                        engraving2 = getAccSecondEngraving(equipment),
                        engraving3 = getAccThirdEngraving(equipment),
                    )
                    characterEquipmentList.add(characterAccessory)
                }

                "어빌리티 스톤" -> {
                    val (eng1Lv, eng1Op) = getABStoneFirstEngraving(equipment)
                    val (eng2Lv, eng2Op) = getABStoneSecondEngraving(equipment)
                    val (eng3Lv, eng3Op) = getABStoneThirdEngraving(equipment)

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

                "팔찌" -> {
                    val bracelet = Bracelet(
                        type = getEquipmentType(equipment),
                        grade = getEquipmentGrade(equipment),
                        name = getAccName(equipment),
                        itemIcon = getItemIcon(equipment),
                        effect = getBraceletEffect(equipment),
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
        return equipment.name.split(" ")[0]
    }

    private fun getItemLevel(equipment: Equipment): String {
        val tooltip = parserEquipmentTooltip(equipment)
        val itemTitleValue = tooltip.getAsJsonObject("Element_001").getAsJsonObject("value")

        return itemTitleValue.get("leftStr2").asString.split(" ")[3]
    }

    private fun getItemQuality(equipment: Equipment): Int {
        val tooltip = parserEquipmentTooltip(equipment)
        val itemTitleValue = tooltip.getAsJsonObject("Element_001").getAsJsonObject("value")

        return itemTitleValue.get("qualityValue").asInt
    }

    private fun getItemIcon(equipment: Equipment): String {
        return equipment.icon
    }

    private fun getElixirFirstOptionAndLevel(equipment: Equipment): Pair<String, String> {
        // Tooltip을 JSON 객체로 파싱
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject
        // Element_008부터 Element_010까지 확인

        for (index in 8..10) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson.getAsJsonObject(elementKey)
                if (element.get("type").asString == "IndentStringGroup") {
                    val value = element.getAsJsonObject("value")
                    if (value.has("Element_000")) {
                        val topStr = value.getAsJsonObject("Element_000").get("topStr").asString
                        if (topStr.contains("엘릭서 효과")) {
                            val contentStr = value.getAsJsonObject("Element_000").getAsJsonObject("contentStr")
                            val firstOption = contentStr.getAsJsonObject("Element_000").get("contentStr").asString

                            val option = firstOption.substringAfter("</FONT> ").substringBefore(" <FONT color='#FFD200'>Lv")
                            val level = firstOption.substringAfter("<FONT color='#FFD200'>Lv.").substringBeforeLast("</FONT>")

                            return Pair(level, option)
                        }
                    }
                }
            }
        }

        return Pair("", "")
    }

    private fun getElixirSecondOptionAndLevel(equipment: Equipment): Pair<String, String> {
        // Tooltip을 JSON 객체로 파싱
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject
        // Element_008부터 Element_010까지 확인

        for (index in 8..10) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson.getAsJsonObject(elementKey)
                if (element.get("type").asString == "IndentStringGroup") {
                    val value = element.getAsJsonObject("value")
                    if (value.has("Element_000")) {
                        val topStr = value.getAsJsonObject("Element_000").get("topStr").asString
                        if (topStr.contains("엘릭서 효과")) {
                            val contentStr = value.getAsJsonObject("Element_000").getAsJsonObject("contentStr")
                            if (contentStr.has("Element_001")) {
                                val secondOption = contentStr.getAsJsonObject("Element_001").get("contentStr").asString

                                val option = secondOption.substringAfter("</FONT> ").substringBefore(" <FONT color='#FFD200'>Lv")
                                val level = secondOption.substringAfter("<FONT color='#FFD200'>Lv.").substringBeforeLast("</FONT>")

                                return Pair(level, option)
                            }
                        }
                    }
                }
            }
        }

        return Pair("", "")
    }

    private fun getTranscendenceLevel(equipment: Equipment): String {
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 7..10) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson.getAsJsonObject(elementKey)
                if (element.get("type").asString == "IndentStringGroup") {
                    val value = element.getAsJsonObject("value")
                    if (value.has("Element_000")) {
                        val topStr = value.getAsJsonObject("Element_000").get("topStr").asString
                        if (topStr.contains("[초월]")) {
                            return topStr.substringAfterLast("<FONT COLOR='#FFD200'>").substringBeforeLast("</FONT>")
                        }
                    }
                }
            }
        }
        return ""
    }

    private fun getTranscendenceTotal(equipment: Equipment): String {
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 7..10) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson.getAsJsonObject(elementKey)
                if (element.get("type").asString == "IndentStringGroup") {
                    val value = element.getAsJsonObject("value")
                    if (value.has("Element_000")) {
                        val topStr = value.getAsJsonObject("Element_000").get("topStr").asString
                        if (topStr.contains("[초월]")) {
                            return topStr.substringAfterLast("</img>")
                        }
                    }
                }
            }
        }
        return ""
    }

    private fun getSetOption(equipment: Equipment): String {
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 7..12) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson.getAsJsonObject(elementKey)
                if (element.get("type").asString == "ItemPartBox") {
                    val value = element.getAsJsonObject("value").get("Element_001").asString
                    if (value.contains("Lv.")) {
                        val setOption = value.substringBefore(" <FONT")
                        val setLevel = value.substringAfterLast("Lv.").substringBefore("</FONT>")

                        return "$setOption $setLevel"
                    }
                }
            }
        }

        return ""
    }

    private fun getHigherUpgradeLevel(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val element = tooltip.getAsJsonObject("Element_005")

        if (element.get("type").asString == "SingleTextBox") {
            val value = element.get("value").asString
            return value.substringAfterLast("<FONT COLOR='#FFD200'>").substringBefore("</FONT>")
        }

        return ""
    }

    private fun getAccName(equipment: Equipment): String {
        return equipment.name
    }

    private fun getAccFirstCombatStat(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val combatStat = tooltip.getAsJsonObject("Element_005").getAsJsonObject("value").get("Element_001").asString

        return combatStat.substringBefore("<BR>")
    }

    private fun getAccSecondCombatStat(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val combatStat = tooltip.getAsJsonObject("Element_005").getAsJsonObject("value").get("Element_001").asString

        return combatStat.substringAfter("<BR>")
    }

    private fun getAccFirstEngraving(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val engraving = tooltip.getAsJsonObject("Element_006").getAsJsonObject("value").getAsJsonObject("Element_000").getAsJsonObject("contentStr")
            .getAsJsonObject("Element_000").get("contentStr").asString
        val option = engraving.substringAfter("<FONT COLOR='#FFFFAC'>").substringBefore("</FONT>")
        val activation = engraving.substringAfter("활성도 +").substringBefore("<BR>")

        return "$option $activation"
    }

    private fun getAccSecondEngraving(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val engraving = tooltip.getAsJsonObject("Element_006").getAsJsonObject("value").getAsJsonObject("Element_000").getAsJsonObject("contentStr")
            .getAsJsonObject("Element_001").get("contentStr").asString
        val option = engraving.substringAfter("<FONT COLOR='#FFFFAC'>").substringBefore("</FONT>")
        val activation = engraving.substringAfter("활성도 +").substringBefore("<BR>")

        return "$option $activation"
    }

    private fun getAccThirdEngraving(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val engraving = tooltip.getAsJsonObject("Element_006").getAsJsonObject("value").getAsJsonObject("Element_000").getAsJsonObject("contentStr")
            .getAsJsonObject("Element_002").get("contentStr").asString
        val option = engraving.substringAfter("<FONT COLOR='#FE2E2E'>").substringBefore("</FONT>")
        val activation = engraving.substringAfter("활성도 +").substringBefore("<BR>")

        return "$option $activation"
    }

    private fun getABStoneHPBonus(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 5..6) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltip.has(elementKey)) {
                val element = tooltip.getAsJsonObject(elementKey)
                if (element.get("type").asString == "ItemPartBox") {
                    val value = element.getAsJsonObject("value")
                    val bonusText = value.get("Element_000").asString
                    if (bonusText.contains("세공 단계 보너스")) {
                        return value.get("Element_001").asString

                    }
                }
            }
        }

        return ""
    }

    private fun getABStoneFirstEngraving(equipment: Equipment): Pair<String, String> {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 5..6) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltip.has(elementKey)) {
                val element = tooltip.getAsJsonObject(elementKey)
                if (element.get("type").asString == "IndentStringGroup") {
                    val topStr = element.getAsJsonObject("value").getAsJsonObject("Element_000").get("topStr").asString
                    if (topStr.contains("무작위 각인 효과")) {
                        val contentStr = element.getAsJsonObject("value").getAsJsonObject("Element_000").getAsJsonObject("contentStr").getAsJsonObject("Element_000").get("contentStr").asString
                        val option = contentStr.substringAfter("<FONT COLOR='#FFFFAC'>").substringBefore("</FONT>")
                        val activation = contentStr.substringAfter("활성도 +").substringBefore("<BR>")

                        return Pair(option, activation)
                    }
                }
            }
        }

        return Pair("", "")
    }

    private fun getABStoneSecondEngraving(equipment: Equipment): Pair<String, String> {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 5..6) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltip.has(elementKey)) {
                val element = tooltip.getAsJsonObject(elementKey)
                if (element.get("type").asString == "IndentStringGroup") {
                    val topStr = element.getAsJsonObject("value").getAsJsonObject("Element_000").get("topStr").asString
                    if (topStr.contains("무작위 각인 효과")) {
                        val contentStr = element.getAsJsonObject("value").getAsJsonObject("Element_000").getAsJsonObject("contentStr").getAsJsonObject("Element_001").get("contentStr").asString
                        val option = contentStr.substringAfter("<FONT COLOR='#FFFFAC'>").substringBefore("</FONT>")
                        val activation = contentStr.substringAfter("활성도 +").substringBefore("<BR>")

                        return Pair(option, activation)
                    }
                }
            }
        }

        return Pair("", "")
    }

    private fun getABStoneThirdEngraving(equipment: Equipment): Pair<String, String> {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        for (index in 5..6) {
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltip.has(elementKey)) {
                val element = tooltip.getAsJsonObject(elementKey)
                if (element.get("type").asString == "IndentStringGroup") {
                    val topStr = element.getAsJsonObject("value").getAsJsonObject("Element_000").get("topStr").asString
                    if (topStr.contains("무작위 각인 효과")) {
                        val contentStr = element.getAsJsonObject("value").getAsJsonObject("Element_000").getAsJsonObject("contentStr").getAsJsonObject("Element_002").get("contentStr").asString
                        val option = contentStr.substringAfter("<FONT COLOR='#FE2E2E'>").substringBefore("</FONT>")
                        val activation = contentStr.substringAfter("활성도 +").substringBefore("<BR>")

                        return Pair(option, activation)
                    }
                }
            }
        }

        return Pair("", "")
    }

    private fun getBraceletEffect(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val effect = tooltip.getAsJsonObject("Element_004").getAsJsonObject("value").get("Element_001").asString

        return processString(effect)
    }

}

fun processString(input: String): String {
    // <BR> 태그를 줄 나누기로 변환
    val withLineBreaks = input.replace(Regex("(?i)<BR>(?=[<\\[])"), "\n<BR>")

    // HTML 태그 제거
    val withoutTags = withLineBreaks.replace(Regex("<[^>]*>"), "")


    // 결과 반환
    return withoutTags
}