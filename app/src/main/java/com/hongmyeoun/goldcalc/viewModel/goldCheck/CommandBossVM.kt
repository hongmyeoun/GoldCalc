package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.CommandBossModel

class CommandBossVM: ViewModel() {
    val cbModel = CommandBossModel()

    var expanded by mutableStateOf(false)
    fun expand(){
        expanded = !expanded
    }

    var totalGold by mutableStateOf(0)
    fun sumGold(va: Int, bi: Int, ku: Int, a: Int, il: Int, ka: Int){
        totalGold = va + bi + ku + a + il + ka
    }

    val valtan = cbModel.valtan
    val valtanSMG = cbModel.valtanSMG
    val valtanCG =  cbModel.valtanCG

    val bi = cbModel.bi
    val biSMG = cbModel.biSMG
    val biCG = cbModel.biCG

    var kuku = cbModel.kuku
    var kukuSMG = cbModel.kukuSMG
    var kukuCG = cbModel.kukuCG

    var abrel = cbModel.abrel
    var abrelSMG = cbModel.abrelSMG
    var abrelCG = cbModel.abrelCG

    var illi = cbModel.illi
    var illiSMG = cbModel.illiSMG
    var illiCG = cbModel.illiCG

    var kamen = cbModel.kamen
    var kamenSMG = cbModel.kamenSMG
    var kamenCG = cbModel.kamenCG
}