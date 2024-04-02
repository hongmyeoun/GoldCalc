package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TwoPhaseVM: ViewModel() {
    var totalGold by mutableStateOf(0)

    fun sumGold(cg1: Int, cg2: Int, smg1: Int, smg2: Int){
        totalGold = cg1 + cg2 - smg1 - smg2
    }
}