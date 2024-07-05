package com.hongmyeoun.goldcalc.viewModel.profile

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.profile.gem.Gem
import com.hongmyeoun.goldcalc.model.profile.skills.Skills
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
            "유물" -> RelicColor
            "전설" -> OrangeQual
            "영웅" -> PurpleQual
            "희귀" -> BlueQual
            else -> GreenQual
        }
        return itemBG
    }

    fun getItemBG(grade: String): Brush {
        val itemBG = when (grade) {
            "유물" -> RelicBG
            "전설" -> LegendaryBG
            "영웅" -> EpicBG
            "희귀" -> RareBG
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

    private fun typeTrans(gem: Gem): Char {
        return if (gem.type.contains('멸')) '멸' else '홍'
    }

    fun gemInfo(gem: Gem): String {
        return "${gem.level}${typeTrans(gem)}"
    }
}