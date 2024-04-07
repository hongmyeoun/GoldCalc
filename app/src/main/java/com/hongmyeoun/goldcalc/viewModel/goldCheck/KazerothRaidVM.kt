package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.KazerothRaidModel
import com.hongmyeoun.goldcalc.model.roomDB.Character

class KazerothRaidVM(val character: Character?) : ViewModel() {
    private val kzModel = KazerothRaidModel(character)

    var totalGold by mutableStateOf(0)
    fun sumGold() {
        totalGold = echidnaTG
    }

    val echidna = kzModel.echidna
    var echidnaTG by mutableStateOf(echidna.onePhase.totalGold + echidna.twoPhase.totalGold)

    fun echidnaTotal() {
        echidnaTG = echidna.onePhase.totalGold + echidna.twoPhase.totalGold
    }

}
