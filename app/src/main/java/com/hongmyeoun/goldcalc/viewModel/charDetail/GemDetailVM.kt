package com.hongmyeoun.goldcalc.viewModel.charDetail

import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.searchedInfo.gem.Gem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.absoluteValue

class GemDetailVM: ViewModel() {
    private val _isDetail = MutableStateFlow(false)
    val isDetail: StateFlow<Boolean> = _isDetail

    fun onDetailClicked() {
        _isDetail.value = !_isDetail.value
    }

    fun countAnnihilationGem(gemList: List<Gem>): Pair<Int, Int> {
        val annihilation = gemList.count { it.type == "멸화" }
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
}