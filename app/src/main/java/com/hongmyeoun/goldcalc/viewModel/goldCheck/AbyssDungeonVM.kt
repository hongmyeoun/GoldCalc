package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.AbyssDungeonModel

class AbyssDungeonVM: ViewModel() {
    private val adModel = AbyssDungeonModel()

    var totalGold by mutableStateOf(0)
    fun sumGold(){
        totalGold = kayangelTG + ivoryTowerTG
    }

    var kayangelTG by mutableStateOf(0)
    private var kayangelOne by mutableStateOf(0)
    private var kayangelTwo by mutableStateOf(0)
    private var kayangelThree by mutableStateOf(0)
    fun kayangelOnePhase(update: Int) {
        kayangelOne = update
        kayangelTotal()
    }
    fun kayangelTwoPhase(update: Int) {
        kayangelTwo = update
        kayangelTotal()
    }
    fun kayangelThreePhase(update: Int) {
        kayangelThree = update
        kayangelTotal()
    }
    private fun kayangelTotal() {
        kayangelTG = kayangelOne + kayangelTwo + kayangelThree
    }

    var ivoryTowerTG by mutableStateOf(0)
    private var ivoryTowerOne by mutableStateOf(0)
    private var ivoryTowerTwo by mutableStateOf(0)
    private var ivoryTowerThree by mutableStateOf(0)
    private var ivoryTowerFour by mutableStateOf(0)
    fun ivoryTowerOnePhase(update: Int) {
        ivoryTowerOne = update
        ivoryTowerTotal()
    }
    fun ivoryTowerTwoPhase(update: Int) {
        ivoryTowerTwo = update
        ivoryTowerTotal()
    }
    fun ivoryTowerThreePhase(update: Int) {
        ivoryTowerThree = update
        ivoryTowerTotal()
    }
    fun ivoryTowerFourPhase(update: Int) {
        ivoryTowerFour = update
        ivoryTowerTotal()
    }
    private fun ivoryTowerTotal() {
        ivoryTowerTG = ivoryTowerOne + ivoryTowerTwo + ivoryTowerThree +ivoryTowerFour
    }

    val kayang = adModel.ka
    val kayangSMG = adModel.kaSMG
    val kayangCG = adModel.kaCG

    val ivT = adModel.ivT
    val ivTSMG = adModel.ivTSMG
    val ivTCG = adModel.ivTCG
}