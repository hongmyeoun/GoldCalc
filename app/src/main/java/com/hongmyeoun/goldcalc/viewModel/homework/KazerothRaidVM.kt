package com.hongmyeoun.goldcalc.viewModel.homework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.homework.KazerothRaidModel
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

class KazerothRaidVM(val character: Character?) : ViewModel() {
    private val kzModel = KazerothRaidModel(character)

    val echidna = kzModel.echidna
    val egir = kzModel.egir

    var totalGold by mutableStateOf(0)

    fun sumGold() {
        echidna.totalGold()
        egir.totalGold()
        totalGold = echidna.totalGold + egir.totalGold
    }

    init {
        sumGold()
    }

    var echiCheck by mutableStateOf(echidna.isChecked)
    var egirCheck by mutableStateOf(egir.isChecked)

    fun onEchiCheck() {
        echiCheck = !echiCheck
        echidna.onShowChecked()
        sumGold()
    }

    fun onEgirCheck() {
        egirCheck = !egirCheck
        egir.onShowChecked()
        sumGold()
    }

}
