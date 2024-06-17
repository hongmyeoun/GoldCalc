package com.hongmyeoun.goldcalc.viewModel.charDetail

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.searchedInfo.skills.Skills
import com.hongmyeoun.goldcalc.ui.theme.BlueQual
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.OrangeQual
import com.hongmyeoun.goldcalc.ui.theme.PurpleQual
import com.hongmyeoun.goldcalc.ui.theme.RelicColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SkillDetailVM: ViewModel() {
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
}