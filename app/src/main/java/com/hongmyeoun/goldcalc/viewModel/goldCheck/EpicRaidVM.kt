package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.EpicRaidModel

class EpicRaidVM: ViewModel() {
    val epModel = EpicRaidModel()

    var expanded by mutableStateOf(false)
    fun expand(){
        expanded = !expanded
    }

    var totalGold by mutableStateOf(0)
    fun sumGold(be: Int){
        totalGold = be
    }

    val behe = epModel.behe
    var beheSMG = epModel.beheSMG
    var beheCG = epModel.beheCG

}