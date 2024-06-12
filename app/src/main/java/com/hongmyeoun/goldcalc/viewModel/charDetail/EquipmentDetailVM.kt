package com.hongmyeoun.goldcalc.viewModel.charDetail

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.AbilityStone
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterAccessory
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterEquipment
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.CharacterItem
import com.hongmyeoun.goldcalc.ui.theme.AncientBG
import com.hongmyeoun.goldcalc.ui.theme.BlueQual
import com.hongmyeoun.goldcalc.ui.theme.EstherBG
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.LegendaryBG
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

}