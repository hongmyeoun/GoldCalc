package com.hongmyeoun.goldcalc.viewModel.profile

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts
import com.hongmyeoun.goldcalc.ui.theme.AncientColor
import com.hongmyeoun.goldcalc.ui.theme.AncientMiddle
import com.hongmyeoun.goldcalc.ui.theme.EpicTextColor
import com.hongmyeoun.goldcalc.ui.theme.EsterColor
import com.hongmyeoun.goldcalc.ui.theme.LegendaryTextColor
import com.hongmyeoun.goldcalc.ui.theme.RareTextColor
import com.hongmyeoun.goldcalc.ui.theme.RelicTextColor
import com.hongmyeoun.goldcalc.ui.theme.UncommonColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArkGridVM : ViewModel() {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _isDetail = MutableStateFlow(false)
    val isDetail: StateFlow<Boolean> = _isDetail

    fun onDetailClicked() {
        _isDetail.value = !_isDetail.value
    }

    fun getGradeBG(grade: String, brightAncient: Boolean = true): Color {
        val itemBG = when (grade) {
            EquipmentConsts.GRADE_ESTHER -> EsterColor
            EquipmentConsts.GRADE_ANCIENT -> if (brightAncient) AncientColor else AncientMiddle
            EquipmentConsts.GRADE_RELIC -> RelicTextColor
            EquipmentConsts.GRADE_LEGENDARY -> LegendaryTextColor
            EquipmentConsts.GRADE_EPIC -> EpicTextColor
            EquipmentConsts.GRADE_RARE -> RareTextColor
            else -> UncommonColor
        }
        return itemBG
    }
}