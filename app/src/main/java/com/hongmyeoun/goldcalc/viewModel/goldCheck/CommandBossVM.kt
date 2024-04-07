package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.CommandBossModel
import com.hongmyeoun.goldcalc.model.roomDB.Character

class CommandBossVM(val character: Character?): ViewModel() {
    private val cbModel = CommandBossModel(character)

    var totalGold by mutableStateOf(0)
    fun sumGold(){
        totalGold = valtanTG + biakissTG + koukuSatonTG + abrelshudTG + illiakanTG + kamenTG
    }

    val valtan = cbModel.valtan
    var valtanTG by mutableStateOf(valtan.onePhase.totalGold + valtan.twoPhase.totalGold)

    fun valtanTotal(){
        valtanTG = valtan.onePhase.totalGold + valtan.twoPhase.totalGold
    }

    val biackiss = cbModel.biackiss
    var biakissTG by mutableStateOf(biackiss.onePhase.totalGold + biackiss.twoPhase.totalGold)

    fun biackissTotal() {
        biakissTG = biackiss.onePhase.totalGold + biackiss.twoPhase.totalGold
    }

    val koukuSaton = cbModel.koukuSaton
    var koukuSatonTG by mutableStateOf(koukuSaton.onePhase.totalGold + koukuSaton.twoPhase.totalGold + koukuSaton.threePhase.totalGold)

    fun koukuSatonTotal() {
        koukuSatonTG = koukuSaton.onePhase.totalGold + koukuSaton.twoPhase.totalGold + koukuSaton.threePhase.totalGold
    }

    val abrelshud = cbModel.abrelshud
    var abrelshudTG by mutableStateOf(abrelshud.onePhase.totalGold + abrelshud.twoPhase.totalGold + abrelshud.threePhase.totalGold + abrelshud.fourPhase.totalGold)

    fun abrelshudTotal() {
        abrelshudTG = abrelshud.onePhase.totalGold + abrelshud.twoPhase.totalGold + abrelshud.threePhase.totalGold + abrelshud.fourPhase.totalGold
    }

    val illiakan = cbModel.illiakan
    var illiakanTG by mutableStateOf(illiakan.onePhase.totalGold + illiakan.twoPhase.totalGold + illiakan.threePhase.totalGold)

    fun illiakanTotal() {
        illiakanTG = illiakan.onePhase.totalGold + illiakan.twoPhase.totalGold + illiakan.threePhase.totalGold
    }


    val kamen = cbModel.kamen
    var kamenTG by mutableStateOf(kamen.onePhase.totalGold + kamen.twoPhase.totalGold + kamen.threePhase.totalGold + kamen.fourPhase.totalGold)

    fun kamenTotal() {
        kamenTG = kamen.onePhase.totalGold + kamen.twoPhase.totalGold + kamen.threePhase.totalGold + kamen.fourPhase.totalGold
    }

    var valtanCheck by mutableStateOf(valtan.isChecked)
    var biaCheck by mutableStateOf(biackiss.isChecked)
    var koukuCheck by mutableStateOf(koukuSaton.isChecked)
    var abreCheck by mutableStateOf(abrelshud.isChecked)
    var illiCheck by mutableStateOf(illiakan.isChecked)
    var kamenCheck by mutableStateOf(kamen.isChecked)

    fun onValtanCheck() {
        valtanCheck = !valtanCheck
    }
    fun onBiaCheck() {
        biaCheck = !biaCheck
    }
    fun onKoukuCheck() {
        koukuCheck = !koukuCheck
    }
    fun onAbreCheck() {
        abreCheck = !abreCheck
    }
    fun onIlliCheck(){
        illiCheck = !illiCheck
    }
    fun onKamenCheck() {
        kamenCheck = !kamenCheck
    }
}