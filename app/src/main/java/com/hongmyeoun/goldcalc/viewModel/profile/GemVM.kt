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

    fun countAnnihilationGem(gemList: List<Gem>): Pair<Int, Int> {
        val annihilation = gemList.count { it.type == EquipmentConsts.DEAL_GEM_3_TIER }
        val crimsonFlame = gemList.size - annihilation

        return Pair(annihilation, crimsonFlame)
    }

    fun calcMaxItemsInEachRow(ann: Int, cri: Int): Pair<Int, Int> {
        val difference = (ann - cri).absoluteValue
        return when {
            difference < 7 -> if (ann > cri) Pair(4, 3) else Pair(3, 4)
            difference < 9 -> if (ann > cri) Pair(5, 2) else Pair(2, 5)
            difference < 11 -> if (ann > cri) Pair(6, 1) else Pair(1, 6)
            else -> if (ann > cri) Pair(7, 0) else Pair(0, 7)
        }
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