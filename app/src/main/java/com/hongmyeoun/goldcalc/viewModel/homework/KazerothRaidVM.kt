package com.hongmyeoun.goldcalc.viewModel.homework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.KazerothRaidModel
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

class KazerothRaidVM(val character: Character?) : ViewModel() {
    private val kzModel = KazerothRaidModel(character)

    val echidna = kzModel.echidna

    var totalGold by mutableStateOf(0)

    fun sumGold() {
        echidna.totalGold()
        totalGold = echidna.totalGold
    }

    init {
        sumGold()
    }

    var echiCheck by mutableStateOf(echidna.isChecked)

    fun onEchiCheck() {
        echiCheck = !echiCheck
        echidna.onShowChecked()
        sumGold()
    }

}
