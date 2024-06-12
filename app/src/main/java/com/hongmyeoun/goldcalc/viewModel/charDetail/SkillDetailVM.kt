package com.hongmyeoun.goldcalc.viewModel.charDetail

import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.searchedInfo.skills.Skills
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
}