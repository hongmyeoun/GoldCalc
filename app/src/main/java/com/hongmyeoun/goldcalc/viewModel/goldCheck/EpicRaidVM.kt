package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.EpicRaidModel

class EpicRaidVM: ViewModel() {
    private val epModel = EpicRaidModel()

    var totalGold by mutableStateOf(0)
    fun sumGold(){
        totalGold = behemothTG
    }

    var behemothTG by mutableStateOf(0)
    private var behemothOne by mutableStateOf(0)
    private var behemothTwo by mutableStateOf(0)
    fun behemothOnePhase(update: Int) {
        behemothOne = update
        behemothTotal()
    }
    fun behemothTwoPhase(update: Int) {
        behemothTwo = update
        behemothTotal()
    }
    fun behemothTotal() {
        behemothTG = behemothOne + behemothTwo
    }

    val behe = epModel.behe
    var beheSMG = epModel.beheSMG
    var beheCG = epModel.beheCG

}