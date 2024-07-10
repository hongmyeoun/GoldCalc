package com.hongmyeoun.goldcalc.viewModel.profile

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.COOLTIME_GEM_2_TIER_SHORT
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.COOLTIME_GEM_3_TIER
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.COOLTIME_GEM_3_TIER_SHORT
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.COOLTIME_GEM_4_TIER
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.COOLTIME_GEM_4_TIER_SHORT
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.DEAL_GEM_2_TIER
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.DEAL_GEM_2_TIER_SHORT
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.DEAL_GEM_3_TIER
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.DEAL_GEM_3_TIER_SHORT
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.DEAL_GEM_4_TIER
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.DEAL_GEM_4_TIER_SHORT
import com.hongmyeoun.goldcalc.model.profile.gem.Gem
import com.hongmyeoun.goldcalc.model.profile.skills.Skills
import com.hongmyeoun.goldcalc.ui.theme.AncientBG
import com.hongmyeoun.goldcalc.ui.theme.AncientMiddle
import com.hongmyeoun.goldcalc.ui.theme.BlueQual
import com.hongmyeoun.goldcalc.ui.theme.EpicBG
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.LegendaryBG
import com.hongmyeoun.goldcalc.ui.theme.OrangeQual
import com.hongmyeoun.goldcalc.ui.theme.PurpleQual
import com.hongmyeoun.goldcalc.ui.theme.RareBG
import com.hongmyeoun.goldcalc.ui.theme.RelicBG
import com.hongmyeoun.goldcalc.ui.theme.RelicColor
import com.hongmyeoun.goldcalc.ui.theme.UncommonBG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SkillVM: ViewModel() {
    private val _isDetail = MutableStateFlow(false)
    val isDetail: StateFlow<Boolean> = _isDetail

    fun onDetailClicked() {
        _isDetail.value = !_isDetail.value
    }

    fun tripodLevel54(skills: List<Skills>): Pair<Int, Int> {
        val fiveLevel = skills.fastSumBy { skill -> skill.tripods.count { tripod -> tripod.isSelected && tripod.level == 5 } }
        val fourLevel = skills.fastSumBy { skill -> skill.tripods.count { tripod -> tripod.isSelected && tripod.level == 4 } }
        return Pair(fiveLevel, fourLevel)
    }

    fun getGradeBG(grade: String): Color {
        val itemBG = when (grade) {
            EquipmentConsts.GRADE_ANCIENT -> AncientMiddle
            EquipmentConsts.GRADE_RELIC -> RelicColor
            EquipmentConsts.GRADE_LEGENDARY -> OrangeQual
            EquipmentConsts.GRADE_EPIC -> PurpleQual
            EquipmentConsts.GRADE_RARE -> BlueQual
            else -> GreenQual
        }
        return itemBG
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

    fun getIndexColor(index: Int): Color {
        val indexColor = when(index) {
            0 -> GreenQual
            1 -> BlueQual
            else -> OrangeQual
        }
        return indexColor
    }

    fun gemFiltering(gemList: List<Gem>, skill: Skills): List<Gem> {
        return gemList.filter { gem -> gem.skill == skill.name }
    }

//    private fun typeTrans(gem: Gem): Char {
//        return if (gem.type.contains('멸')) '멸' else '홍'
//    }

    private fun typeTrans(gem: Gem): Char {
        return when(gem.type) {
            DEAL_GEM_4_TIER -> DEAL_GEM_4_TIER_SHORT
            COOLTIME_GEM_4_TIER -> COOLTIME_GEM_4_TIER_SHORT
            DEAL_GEM_3_TIER -> DEAL_GEM_3_TIER_SHORT
            COOLTIME_GEM_3_TIER -> COOLTIME_GEM_3_TIER_SHORT
            DEAL_GEM_2_TIER -> DEAL_GEM_2_TIER_SHORT
            else -> COOLTIME_GEM_2_TIER_SHORT
        }
    }

    fun gemInfo(gem: Gem): String {
        return "${gem.level}${typeTrans(gem)}"
    }
}