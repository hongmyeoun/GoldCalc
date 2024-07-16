package com.hongmyeoun.goldcalc.viewModel.profile

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.GRADE_EPIC
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.GRADE_LEGENDARY
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.PENALTY_ENGRAVINGS
import com.hongmyeoun.goldcalc.model.profile.engravings.SkillEngravings
import com.hongmyeoun.goldcalc.ui.theme.EpicTextColor
import com.hongmyeoun.goldcalc.ui.theme.LegendaryTextColor
import com.hongmyeoun.goldcalc.ui.theme.RelicTextColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EngravingVM: ViewModel() {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog
    fun onDismissRequest() {
        _engravingName.value = ""
        _showDialog.value = false
    }

    fun onClicked(engravingName: String) {
        _engravingName.value = engravingName
        _showDialog.value = true
    }

    private val _engravingName = MutableStateFlow("")

    fun getEngraving(skillEngravings: List<SkillEngravings>): SkillEngravings? {
        return skillEngravings.find { it.name == _engravingName.value }
    }

    fun stoneImg(name: String): Int {
        return if (name in PENALTY_ENGRAVINGS) R.drawable.img_penalty_ability_stone else R.drawable.img_awaken_ability_stone
    }

    fun engGrade(grade: String): Int {
        return when (grade) {
            GRADE_EPIC -> R.drawable.img_epic_awake
            GRADE_LEGENDARY -> R.drawable.img_legend_awake
            else -> R.drawable.img_relic_awake
        }
    }

    fun textColor(grade: String): Color {
        return when (grade) {
            GRADE_EPIC -> EpicTextColor
            GRADE_LEGENDARY -> LegendaryTextColor
            else -> RelicTextColor
        }
    }
}