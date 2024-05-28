package com.hongmyeoun.goldcalc.model.lostArkApi

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.gson.Gson
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

    suspend fun getCharEquipment(context: Context, characterName: String): List<Equipment>? {
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
                    Log.d("캐릭터 장비", "${response.body()}")
                    val equipmentList = response.body()
                    response.body()
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    fun dataFilter(equipment: Equipment): String {
        val filterResults = mutableMapOf<String, Boolean>()

        filterResults["E"] = elixirFilter(equipment)
        filterResults["s"] = elixirSetOptionFilter(equipment)
        filterResults["T"] = transendenceFilter(equipment)
        filterResults["S"] = higherUpgradedFilter(equipment)
        filterResults["M"] = maxLevelFilter(equipment)

        return filterResults.filter { it.value }.keys.joinToString("")
    }

    fun elixirFilter(equipment: Equipment): Boolean {
        // Tooltip을 JSON 객체로 파싱
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject
        // Element_008부터 Element_010까지 확인
        return (8..10).any { index ->
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson.getAsJsonObject(elementKey)
                if (element.get("type").asString == "IndentStringGroup") {
                    val value = element.getAsJsonObject("value")
                    if (value.has("Element_000")) {
                        val contentStr = value.getAsJsonObject("Element_000").get("topStr").asString
                        return@any contentStr.contains("엘릭서 효과")
                    }
                }
            }
            false
        }
    }

    fun elixirSetOptionFilter(equipment: Equipment): Boolean {
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject

        return (9..11).any { index ->
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson.getAsJsonObject(elementKey)
                if (element.get("type").asString == "IndentStringGroup") {
                    val value = element.getAsJsonObject("value")
                    if (value.has("Element_000")) {
                        val contentStr = value.getAsJsonObject("Element_000").get("topStr").asString
                        return@any contentStr.contains("연성 추가 효과")
                    }
                }
            }
            false
        }
    }

    fun transendenceFilter(equipment: Equipment): Boolean {
        val tooltipJson = JsonParser.parseString(equipment.tooltip).asJsonObject

        return (7..10).any { index ->
            val elementKey = "Element_${String.format("%03d", index)}"
            if (tooltipJson.has(elementKey)) {
                val element = tooltipJson.getAsJsonObject(elementKey)
                if (element.get("type").asString == "IndentStringGroup") {
                    val value = element.getAsJsonObject("value")
                    if (value.has("Element_000")) {
                        val contentStr = value.getAsJsonObject("Element_000").get("topStr").asString
                        return@any contentStr.contains("[초월]")
                    }
                }
            }
            false
        }
    }

    fun higherUpgradedFilter(equipment: Equipment): Boolean {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject

        val element = tooltip.getAsJsonObject("Element_005")
        return element.get("type").asString == "SingleTextBox"
    }

    fun maxLevelFilter(equipment: Equipment): Boolean {
        return if (equipment.type == "무기" && equipment.grade == "에스더" && equipment.name.contains("8")) {
            true
        } else equipment.name.contains("25")
    }


//    suspend fun getCharDetails(context: Context, characterName: String): List<Equipment>? {
//        return withContext(Dispatchers.IO) {
//            val gson = GsonBuilder().setLenient().create()
//            val retrofit = Retrofit.Builder()
//                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(authorizationHeader(context))
//                .build()
//
//            val lostArkApiService = retrofit.create(LostArkApiService::class.java)
//
//            try {
//                val response = lostArkApiService.getCharacterEquipment(characterName).execute()
//                if (response.isSuccessful) {
//                    response.body()?.map { equipment ->
//                        equipment.tooltip = parseTooltip(equipment.tooltip?:"")
//                        equipment
//                    }
//                } else {
//                    null
//                }
//            } catch (e: IOException) {
//                null
//            }
//        }
//    }

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

class CharacterEquipmentDetail(jsonString: String) {
    private val gson = Gson()
    private val equipments: List<Equipment> = gson.fromJson(jsonString, Array<Equipment>::class.java).toList()

    fun parserEquipmentTooltip(equipment: Equipment): JsonObject {
        return JsonParser.parseString(equipment.tooltip).asJsonObject
    }

    fun getCharacterEquipmentDetails(): List<CharacterItem> {
        val characterEquipmentList = mutableListOf<CharacterItem>()

        for (equipment in equipments) {
            when (equipment.type) {
                "무기", "투구", "상의", "하의", "장갑", "어깨" -> {
                    val characterEquipment = CharacterEquipment(
                        type = getEquipmentType(equipment),
                        grade = getEquipmentGrade(equipment),
                        upgradeLevel = getEquipmentUpgradeLevel(equipment),
                        itemLevel = getItemLevel(equipment),
                        itemQuality = getItemQuality(equipment),
                        itemIcon = getItemIcon(equipment),
                        elixirFirst = getElixirFirstOptionAndLevel(equipment),
                        elixirSecond = getElixirSecondOptionAndLevel(equipment),
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
                    val abilityStone = AbilityStone(
                        type = getEquipmentType(equipment),
                        grade = getEquipmentGrade(equipment),
                        name = getAccName(equipment),
                        itemIcon = getItemIcon(equipment),
                        hpBonus = getABStoneHPBonus(equipment),
                        engraving1 = getABStoneFirstEngraving(equipment),
                        engraving2 = getABStoneSecondEngraving(equipment),
                        engraving3 = getABStoneThirdEngraving(equipment),
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

    private fun getElixirFirstOptionAndLevel(equipment: Equipment): String {
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

                            return "$option $level"
                        }
                    }
                }
            }
        }

        return ""
    }

    private fun getElixirSecondOptionAndLevel(equipment: Equipment): String {
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

                                return "$option $level"
                            }
                        }
                    }
                }
            }
        }

        return ""
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
                            val contentStr = value.getAsJsonObject("Element_000").getAsJsonObject("contentStr")
                            val totalLevelContentStr = contentStr.getAsJsonObject("Element_001").get("contentStr").asString
                            return totalLevelContentStr.substringAfterLast("</img>").substringBeforeLast("개</FONT>")
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
        val bonus = tooltip.getAsJsonObject("Element_005").getAsJsonObject("value").get("Element_001").asString

        return bonus.substringAfter("체력 ")
    }

    private fun getABStoneFirstEngraving(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val engraving = tooltip.getAsJsonObject("Element_006").getAsJsonObject("value").getAsJsonObject("Element_000").getAsJsonObject("contentStr")
            .getAsJsonObject("Element_000").get("contentStr").asString
        val option = engraving.substringAfter("<FONT COLOR='#FFFFAC'>").substringBefore("</FONT>")
        val activation = engraving.substringAfter("활성도 +").substringBefore("<BR>")

        return "$option $activation"
    }

    private fun getABStoneSecondEngraving(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val engraving = tooltip.getAsJsonObject("Element_006").getAsJsonObject("value").getAsJsonObject("Element_000").getAsJsonObject("contentStr")
            .getAsJsonObject("Element_001").get("contentStr").asString
        val option = engraving.substringAfter("<FONT COLOR='#FFFFAC'>").substringBefore("</FONT>")
        val activation = engraving.substringAfter("활성도 +").substringBefore("<BR>")

        return "$option $activation"
    }

    private fun getABStoneThirdEngraving(equipment: Equipment): String {
        val tooltip = JsonParser.parseString(equipment.tooltip).asJsonObject
        val engraving = tooltip.getAsJsonObject("Element_006").getAsJsonObject("value").getAsJsonObject("Element_000").getAsJsonObject("contentStr")
            .getAsJsonObject("Element_002").get("contentStr").asString
        val option = engraving.substringAfter("<FONT COLOR='#FE2E2E'>").substringBefore("</FONT>")
        val activation = engraving.substringAfter("활성도 +").substringBefore("<BR>")

        return "$option $activation"
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