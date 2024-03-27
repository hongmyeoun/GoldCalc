package com.hongmyeoun.goldcalc.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.KazerothRaidModel

class KazerothRaidVM: ViewModel() {
    val kzModel = KazerothRaidModel()

    var expanded by mutableStateOf(false)
    fun expand(){
        expanded = !expanded
    }

    var totalGold by mutableStateOf(0)
    fun sumGold(echidna: Int){
        totalGold = echidna
    }

    val echi = kzModel.echi
    var echiSMG = kzModel.echiSMG
    var echiCG = kzModel.echiCG
}