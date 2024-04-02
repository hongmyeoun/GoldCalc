package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GoldSettingVM: ViewModel() {
    var plusGold by mutableStateOf("0")
    var minusGold by mutableStateOf("0")
    private var totalGold by mutableStateOf(0)

    fun plusGoldValue(newValue: String){
        plusGold = newValue.filter { it.isDigit() }
    }

    fun minusGoldValue(newValue: String){
        minusGold = newValue.filter { it.isDigit() }
    }

    fun calcTotalGold(cb: Int, ad: Int, kz: Int, ep: Int): Int {
        val plusGoldInt = plusGold.toIntOrNull()?:0
        val minusGoldInt = minusGold.toIntOrNull()?:0
        totalGold = cb + ad + kz + ep + plusGoldInt - minusGoldInt
        return totalGold
    }

}