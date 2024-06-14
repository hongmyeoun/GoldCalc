package com.hongmyeoun.goldcalc.viewModel.charDetail

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.AncientBG
import com.hongmyeoun.goldcalc.ui.theme.AncientColor
import com.hongmyeoun.goldcalc.ui.theme.AncientMiddle
import com.hongmyeoun.goldcalc.ui.theme.BlueQual
import com.hongmyeoun.goldcalc.ui.theme.EsterColor
import com.hongmyeoun.goldcalc.ui.theme.EstherBG
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.LegendaryBG
import com.hongmyeoun.goldcalc.ui.theme.LegendaryColor
import com.hongmyeoun.goldcalc.ui.theme.OrangeQual
import com.hongmyeoun.goldcalc.ui.theme.PurpleQual
import com.hongmyeoun.goldcalc.ui.theme.RedQual
import com.hongmyeoun.goldcalc.ui.theme.RelicBG
import com.hongmyeoun.goldcalc.ui.theme.RelicColor
import com.hongmyeoun.goldcalc.ui.theme.YellowQual
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EquipmentDetailVM(characterEquipment: List<CharacterItem>): ViewModel() {
    private val _accessoryAvgQuality = MutableStateFlow(0.0)
    val accessoryAvgQuality: StateFlow<Double> = _accessoryAvgQuality

    private val _setOption = MutableStateFlow("세트효과 없음")
    val setOption: StateFlow<String> = _setOption

    private val _totalCombatStat = MutableStateFlow(0)
    val totalCombatStat: StateFlow<Int> = _totalCombatStat

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog
    fun onDismissRequest() {
        _showDialog.value = false
    }

    fun onClicked() {
        _showDialog.value = true
    }

    init {
        _accessoryAvgQuality.value = getAccessoryAvgQuality(characterEquipment)
        _setOption.value = getSetoption(characterEquipment)
        _totalCombatStat.value = getSumCombatStat(characterEquipment)
    }

    private fun getAccessoryAvgQuality(equipment: List<CharacterItem>): Double {
        val accessoryTotalQuality = equipment.filterIsInstance<CharacterAccessory>().map { it.itemQuality }
        return if (accessoryTotalQuality.isNotEmpty()) accessoryTotalQuality.average() else 0.0
    }

    private fun getSetoption(equipment: List<CharacterItem>): String {
        val setOptions = equipment.filterIsInstance<CharacterEquipment>().map { it.setOption }
        val setOptionGroups = setOptions.map { it.split(" ") }.groupBy({ it[0] }, { it[1].toInt() })
        val formattedSetOptions = setOptionGroups.map { (option, levels) ->
            val count = levels.size
            val averageLevel = levels.average()

            val formattedLevel = if (averageLevel % 1 == 0.0) averageLevel.toInt().toString() else "%.1f".format(averageLevel)

            "$count$option Lv.$formattedLevel"
        }

        return formattedSetOptions.joinToString(", ")
    }

    private fun getSumCombatStat(equipment: List<CharacterItem>): Int {
        val combatStat = equipment.filterIsInstance<CharacterAccessory>().map { it.combatStat1 }
        val combatStatOne = combatStat.sumOf { it.split(" ")[1].removePrefix("+").toInt() }
        val combatStatTwo = equipment.filterIsInstance<CharacterAccessory>()[0].combatStat2.split(" ")[1].removePrefix("+").toInt()

        return combatStatOne + combatStatTwo
    }

    fun setOptionName(equipment: CharacterEquipment): String {
        return equipment.setOption.split(" ").first()
    }

    fun abilityStone(equipment: AbilityStone): String {
        return equipment.engraving1Lv.substringAfter("Lv. ") + equipment.engraving2Lv.substringAfter("Lv. ") + equipment.engraving3Lv.substringAfter("Lv. ")
    }

    fun getItemBG(grade: String): Brush {
        val itemBG = when (grade) {
            "에스더" -> EstherBG
            "고대" -> AncientBG
            "유물" -> RelicBG
            else -> LegendaryBG
        }
        return itemBG
    }

    fun getGradeBG(grade: String, brightAncient: Boolean = true): Color {
        val itemBG = when (grade) {
            "에스더" -> EsterColor
            "고대" -> if(brightAncient) AncientColor else AncientMiddle
            "유물" -> RelicColor
            else -> LegendaryColor
        }
        return itemBG
    }

    fun getQualityColor(quality: String): Color {
        return if (quality.toInt() >= 100) {
            OrangeQual
        } else if (quality.toInt() in 90 until 100) {
            PurpleQual
        } else if (quality.toInt() in 70 until 90) {
            BlueQual
        } else if (quality.toInt() in 30 until 70) {
            GreenQual
        } else if (quality.toInt() in 10 until 30) {
            YellowQual
        } else {
            RedQual
        }
    }

    fun getElixirColor(level: String): Color {
        return when (level.toInt()) {
            5 -> RelicColor
            4 -> OrangeQual
            3 -> PurpleQual
            2 -> BlueQual
            else -> GreenQual
        }
    }

    fun getStoneColor(level: String): Color {
        return if (level.toInt() >= 9) {
            OrangeQual
        } else if (level.toInt() in 7 until 9) {
            PurpleQual
        } else if (level.toInt() in 5 until 7) {
            BlueQual
        } else {
            RedQual
        }
    }

    fun simplyEngravingName(engraving: String): String {
        val setOption = onlySetOption(engraving)
        val level = engraving.split(" ").last()
        return "$setOption $level"
    }

    fun onlySetOption(engraving: String): String {
        val regex = Regex("""^[^\d]+""")
        val match = regex.find(engraving)?.value?.trim()?:""
        return getSimpleEngraving(match)
    }

    fun getSimpleEngraving(engraving: String): String {
        val simpleName = mapOf(
            "결투의 대가" to "결대",
            "급소 타격" to "급타",
            "기습의 대가" to "기습",
            "돌격대장" to "돌대",
            "속전속결" to "속속",
            "슈퍼 차지" to "슈차",
            "아드레날린" to "아드",
            "에테르 포식자" to "에포",
            "예리한 둔기" to "예둔",
            "저주받은 인형" to "저받",
            "정기 흡수" to "정흡",
            "중갑 착용" to "중갑",
            "정밀 단도" to "정단",
            "질량 증가" to "질증",
            "최대 마나 증가" to "최마증",
            "타격의 대가" to "타대",
            "폭발물 전문가" to "폭전",
            "분노의 망치" to "분망",
            "중력 수련" to "중수",
            "광전사의 비기" to "비기",
            "고독한 기사" to "고기",
            "전투 태세" to "전태",
            "축복의 오라" to "축오",
            "세맥타통" to "세맥",
            "역천지체" to "역천",
            "수라의 길" to "수라",
            "권왕파천무" to "권왕",
            "일격필살" to "일격",
            "극의: 체술" to "체술",
            "충격 단련" to "충단",
            "사냥의 시간" to "사시",
            "피스메이커" to "피메",
            "강화 무기" to "강무",
            "포격 강화" to "포강",
            "화력 강화" to "화강",
            "진화의 유산" to "유산",
            "아르데타인의 기술" to "기술",
            "두 번째 동료" to "두동",
            "죽음의 습격" to "죽습",
            "절실한 구원" to "절구",
            "넘치는 교감" to "교감",
            "상급 소환사" to "상소",
            "황제의 칙령" to "황제",
            "황후의 은총" to "황후",
            "멈출 수 없는 충동" to "충동",
            "완벽한 억제" to "억제",
            "달의 소리" to "달소",
            "잔재된 기운" to "잔재",
            "그믐의 경계" to "그믐",
            "만월의 집행자" to "만월",
            "질풍노도" to "질풍"
        )
        return simpleName[engraving] ?: engraving
    }
}