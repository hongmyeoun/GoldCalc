package com.hongmyeoun.goldcalc.viewModel.homework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.homework.ShadowRaidModel
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

class ShadowRaidVM(val character: Character?): ViewModel() {
    private val sdModel = ShadowRaidModel(character)

    val serca = sdModel.serca
    val belgardin = sdModel.belgardin

    var totalGold by mutableStateOf(0)

    fun sumGold() {
        serca.totalGold()
        totalGold = serca.totalGold + belgardin.totalGold
    }

    init {
        sumGold()
    }

    var sercaCheck by mutableStateOf(serca.isChecked)
    var belgardinCheck by mutableStateOf(belgardin.isChecked)

    fun onSercaCheck() {
        sercaCheck = !sercaCheck
        serca.onShowChecked()
        sumGold()
    }

    fun onBelgardinCheck() {
        belgardinCheck = !belgardinCheck
        belgardin.onShowChecked()
        sumGold()
    }
}