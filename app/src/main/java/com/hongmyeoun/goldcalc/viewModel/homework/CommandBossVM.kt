package com.hongmyeoun.goldcalc.viewModel.homework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.homework.CommandBossModel
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

class CommandBossVM(val character: Character?): ViewModel() {
    private val cbModel = CommandBossModel(character)

    val valtan = cbModel.valtan
    val biackiss = cbModel.biackiss
    val koukuSaton = cbModel.koukuSaton
    val abrelshud = cbModel.abrelshud
    val illiakan = cbModel.illiakan
    val kamen = cbModel.kamen

    var totalGold by mutableStateOf(0)

    fun sumGold(){
        valtan.totalGold()
        biackiss.totalGold()
        koukuSaton.totalGold()
        abrelshud.totalGold()
        illiakan.totalGold()
        kamen.totalGold()

        totalGold = valtan.totalGold + biackiss.totalGold + koukuSaton.totalGold + abrelshud.totalGold + illiakan.totalGold + kamen.totalGold
    }

    init {
        sumGold()
    }

    var valtanCheck by mutableStateOf(valtan.isChecked)
    var biaCheck by mutableStateOf(biackiss.isChecked)
    var koukuCheck by mutableStateOf(koukuSaton.isChecked)
    var abreCheck by mutableStateOf(abrelshud.isChecked)
    var illiCheck by mutableStateOf(illiakan.isChecked)
    var kamenCheck by mutableStateOf(kamen.isChecked)

    fun onValtanCheck() {
        valtanCheck = !valtanCheck
        valtan.onShowChecked()
        sumGold()
    }
    fun onBiaCheck() {
        biaCheck = !biaCheck
        biackiss.onShowChecked()
        sumGold()
    }
    fun onKoukuCheck() {
        koukuCheck = !koukuCheck
        koukuSaton.onShowChecked()
        sumGold()
    }
    fun onAbreCheck() {
        abreCheck = !abreCheck
        abrelshud.onShowChecked()
        sumGold()
    }
    fun onIlliCheck(){
        illiCheck = !illiCheck
        illiakan.onShowChecked()
        sumGold()
    }
    fun onKamenCheck() {
        kamenCheck = !kamenCheck
        kamen.onShowChecked()
        sumGold()
    }
}