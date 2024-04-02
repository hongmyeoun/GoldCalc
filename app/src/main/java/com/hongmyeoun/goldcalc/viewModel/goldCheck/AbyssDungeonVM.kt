package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.AbyssDungeonModel

class AbyssDungeonVM: ViewModel() {
    val adModel = AbyssDungeonModel()

    var expanded by mutableStateOf(false)
    fun expand(){
        expanded = !expanded
    }

    var totalGold by mutableStateOf(0)
    fun sumGold(ka: Int, sang: Int){
        totalGold = ka + sang
    }

    val kayang = adModel.ka
    val kayangSMG = adModel.kaSMG
    val kayangCG = adModel.kaCG

    val ivT = adModel.ivT
    val ivTSMG = adModel.ivTSMG
    val ivTCG = adModel.ivTCG
}