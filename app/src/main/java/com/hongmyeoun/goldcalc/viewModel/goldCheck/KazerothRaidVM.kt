package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.KazerothRaidModel

class KazerothRaidVM: ViewModel() {
    val kzModel = KazerothRaidModel()

    var totalGold by mutableStateOf(0)
    fun sumGold(){
        totalGold = echidnaTG
    }

    var echidnaTG by mutableStateOf(0)
    private var echidnaOne by mutableStateOf(0)
    private var echidnaTwo by mutableStateOf(0)
    fun echidnaOnePhase(update: Int) {
        echidnaOne = update
        echidnaTotal()
    }
    fun echidnaTwoPhase(update: Int) {
        echidnaTwo = update
        echidnaTotal()
    }
    fun echidnaTotal() {
        echidnaTG = echidnaOne + echidnaTwo
    }

    val echi = kzModel.echi
    var echiSMG = kzModel.echiSMG
    var echiCG = kzModel.echiCG
}