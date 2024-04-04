package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.CommandBossModel

class CommandBossVM: ViewModel() {
    private val cbModel = CommandBossModel()

    var totalGold by mutableStateOf(0)
    fun sumGold(){
        totalGold = valtanTG + biakissTG + koukuSatonTG + abrelshudTG + illiakanTG + kamenTG
    }

    var valtanTG by mutableStateOf(0)
    private var valtanOne by mutableStateOf(0)
    private var valtanTwo by mutableStateOf(0)
    fun valtanOnePhase(update: Int) {
        valtanOne = update
        valtanTotal()
    }
    fun valtanTwoPhase(update: Int) {
        valtanTwo = update
        valtanTotal()
    }
    private fun valtanTotal(){
        valtanTG = valtanOne + valtanTwo
    }

    var biakissTG by mutableStateOf(0)
    private var biakissOne by mutableStateOf(0)
    private var biakissTwo by mutableStateOf(0)
    fun biakissOnePhase(update: Int) {
        biakissOne = update
        biakissTotal()
    }
    fun biakissTwoPhase(update: Int) {
        biakissTwo = update
        biakissTotal()
    }
    private fun biakissTotal() {
        biakissTG = biakissOne + biakissTwo
    }

    var koukuSatonTG by mutableStateOf(0)
    private var koukuSatonOne by mutableStateOf(0)
    private var koukuSatonTwo by mutableStateOf(0)
    private var koukuSatonThree by mutableStateOf(0)
    fun koukuSatonOnePhase(update: Int) {
        koukuSatonOne = update
        koukuSatonTotal()
    }
    fun koukuSatontwoPhase(update: Int) {
        koukuSatonTwo = update
        koukuSatonTotal()
    }
    fun koukuSatonThreePhase(update: Int) {
        koukuSatonThree = update
        koukuSatonTotal()
    }
    private fun koukuSatonTotal() {
        koukuSatonTG = koukuSatonOne + koukuSatonTwo + koukuSatonThree
    }


    var illiakanTG by mutableStateOf(0)
    private var illiakanOne by mutableStateOf(0)
    private var illiakanTwo by mutableStateOf(0)
    private var illiakanThree by mutableStateOf(0)
    fun illiakanOnePhase(update: Int) {
        illiakanOne = update
        illiakanTotal()
    }
    fun illiakantwoPhase(update: Int) {
        illiakanTwo = update
        illiakanTotal()
    }
    fun illiakanThreePhase(update: Int) {
        illiakanThree = update
        illiakanTotal()
    }
    fun illiakanTotal() {
        illiakanTG = illiakanOne + illiakanTwo + illiakanThree
    }

    var abrelshudTG by mutableStateOf(0)
    private var abrelshudOne by mutableStateOf(0)
    private var abrelshudTwo by mutableStateOf(0)
    private var abrelshudThree by mutableStateOf(0)
    private var abrelshudFour by mutableStateOf(0)
    fun abrelshudOnePhase(update: Int) {
        abrelshudOne = update
        abrelshudTotal()
    }
    fun abrelshudTwoPhase(update: Int) {
        abrelshudTwo = update
        abrelshudTotal()
    }
    fun abrelshudThreePhase(update: Int) {
        abrelshudThree = update
        abrelshudTotal()
    }
    fun abrelshudFourPhase(update: Int) {
        abrelshudFour = update
        abrelshudTotal()
    }
    fun abrelshudTotal() {
        abrelshudTG = abrelshudOne + abrelshudTwo + abrelshudThree + abrelshudFour
    }

    var kamenTG by mutableStateOf(0)
    private var kamenOne by mutableStateOf(0)
    private var kamenTwo by mutableStateOf(0)
    private var kamenThree by mutableStateOf(0)
    private var kamenFour by mutableStateOf(0)
    fun kamenOnePhase(update: Int) {
        kamenOne = update
        kamenTotal()
    }
    fun kamentwoPhase(update: Int) {
        kamenTwo = update
        kamenTotal()
    }
    fun kamenThreePhase(update: Int) {
        kamenThree = update
        kamenTotal()
    }
    fun kamenFourPhase(update: Int) {
        kamenFour = update
        kamenTotal()
    }
    fun kamenTotal() {
        kamenTG = kamenOne + kamenTwo + kamenThree + kamenFour
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