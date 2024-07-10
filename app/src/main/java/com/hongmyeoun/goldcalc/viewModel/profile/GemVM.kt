package com.hongmyeoun.goldcalc.viewModel.profile

import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.model.profile.gem.Gem
import com.hongmyeoun.goldcalc.ui.theme.AncientBG
import com.hongmyeoun.goldcalc.ui.theme.EpicBG
import com.hongmyeoun.goldcalc.ui.theme.LegendaryBG
import com.hongmyeoun.goldcalc.ui.theme.RareBG
import com.hongmyeoun.goldcalc.ui.theme.RelicBG
import com.hongmyeoun.goldcalc.ui.theme.UncommonBG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.absoluteValue

class GemVM: ViewModel() {
    private val _isDetail = MutableStateFlow(false)
    val isDetail: StateFlow<Boolean> = _isDetail

    fun onDetailClicked() {
        _isDetail.value = !_isDetail.value
    }

    fun countDealGem(gemList: List<Gem>): Pair<Int, Int> {
        val deal = gemList.count { it.type in EquipmentConsts.DEAL_GEM_LIST }
        val coolTime = gemList.size - deal

        return Pair(deal, coolTime)
    }

    fun calcMaxItemsInEachRow(deal: Int, cool: Int): Pair<Int, Int> {
        val difference = (deal - cool).absoluteValue
        return when {
            difference < 7 -> if (deal > cool) Pair(4, 3) else Pair(3, 4)
            difference < 9 -> if (deal > cool) Pair(5, 2) else Pair(2, 5)
            difference < 11 -> if (deal > cool) Pair(6, 1) else Pair(1, 6)
            else -> if (deal > cool) Pair(7, 0) else Pair(0, 7)
        }
    }

    fun t4Deal(gemList: List<Gem>): Int {
        return gemList.count { it.type == EquipmentConsts.DEAL_GEM_4_TIER }
    }

    fun t4Cool(gemList: List<Gem>): Int {
        return gemList.count { it.type == EquipmentConsts.COOLTIME_GEM_4_TIER }
    }

    fun t3Deal(gemList: List<Gem>): Int {
        return gemList.count { it.type == EquipmentConsts.DEAL_GEM_3_TIER }
    }

    fun t3Cool(gemList: List<Gem>): Int {
        return gemList.count { it.type == EquipmentConsts.COOLTIME_GEM_3_TIER }
    }

    fun t2Deal(gemList: List<Gem>): Int {
        return gemList.count { it.type == EquipmentConsts.DEAL_GEM_2_TIER }
    }

    fun t2Cool(gemList: List<Gem>): Int {
        return gemList.count { it.type == EquipmentConsts.COOLTIME_GEM_2_TIER }
    }

    fun totalIncrease(gemList: List<Gem>): String {
        val totalAttack = gemList.sumOf {
            val numberString = extractNumber(it.option)
            val number = numberString.toDoubleOrNull()
            number ?: 0.0
        }
        return if (totalAttack > 0.0) String.format("%.2f%%", totalAttack) else ""
    }

    private fun extractNumber(input: String): String {
        val regex = Regex("[0-9.]+")
        val matchResult = regex.find(input)
        return matchResult?.value ?: ""
    }

    fun getItemBG(grade: String): Brush {
        val itemBG = when (grade) {
            EquipmentConsts.GRADE_ANCIENT -> AncientBG
            EquipmentConsts.GRADE_RELIC -> RelicBG
            EquipmentConsts.GRADE_LEGENDARY -> LegendaryBG
            EquipmentConsts.GRADE_EPIC -> EpicBG
            EquipmentConsts.GRADE_RARE -> RareBG
            else -> UncommonBG
        }
        return itemBG
    }

}