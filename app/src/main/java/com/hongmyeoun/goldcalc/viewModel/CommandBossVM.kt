package com.hongmyeoun.goldcalc.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CommandBossVM: ViewModel() {
    var expanded by mutableStateOf(false)
    var totalGold by mutableStateOf(0)

    fun expand(){
        expanded = !expanded
    }

    fun sumGold(va: Int, bi: Int, ku: Int, a: Int, il: Int, ka: Int){
        totalGold = va + bi + ku + a + il + ka
    }
}