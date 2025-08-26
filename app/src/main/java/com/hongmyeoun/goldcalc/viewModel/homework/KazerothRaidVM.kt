package com.hongmyeoun.goldcalc.viewModel.homework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.homework.KazerothRaidModel
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

class KazerothRaidVM(val character: Character?) : ViewModel() {
    private val kzModel = KazerothRaidModel(character)

    val echidna = kzModel.echidna
    val egir = kzModel.egir
    val abrelshud2 = kzModel.abrelshud
    val mordum = kzModel.mordum
    val armoche = kzModel.armoche
    val kazeroth = kzModel.kazeroth

    var totalGold by mutableStateOf(0)

    fun sumGold() {
        echidna.totalGold()
        egir.totalGold()
        abrelshud2.totalGold()
        mordum.totalGold()
        armoche.totalGold()
        kazeroth.totalGold()
        totalGold = echidna.totalGold + egir.totalGold + abrelshud2.totalGold + mordum.totalGold + armoche.totalGold + kazeroth.totalGold
    }

    init {
        sumGold()
    }

    var echiCheck by mutableStateOf(echidna.isChecked)
    var egirCheck by mutableStateOf(egir.isChecked)
    var abrelCheck by mutableStateOf(abrelshud2.isChecked)
    var mordumCheck by mutableStateOf(mordum.isChecked)
    var armocheCheck by mutableStateOf(armoche.isChecked)
    var kazerothCheck by mutableStateOf(kazeroth.isChecked)

    fun onEchiCheck() {
        echiCheck = !echiCheck
        echidna.onShowChecked()
        sumGold()
    }

    fun onEgirCheck() {
        egirCheck = !egirCheck
        egir.onShowChecked()
        sumGold()
    }

    fun onAbrelCheck() {
        abrelCheck = !abrelCheck
        abrelshud2.onShowChecked()
        sumGold()
    }

    fun onKamenCheck() {
        mordumCheck = !mordumCheck
        mordum.onShowChecked()
        sumGold()
    }

    fun onArmocheCheck() {
        armocheCheck = !armocheCheck
        armoche.onShowChecked()
        sumGold()
    }

    fun onKazerothCheck() {
        kazerothCheck = !kazerothCheck
        kazeroth.onShowChecked()
        sumGold()
    }
}
