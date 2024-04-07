package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.AbyssDungeonModel
import com.hongmyeoun.goldcalc.model.roomDB.Character

class AbyssDungeonVM(val character: Character?): ViewModel() {
    private val adModel = AbyssDungeonModel(character)

    var totalGold by mutableStateOf(0)
    fun sumGold(){
        totalGold = kayangelTG + ivoryTowerTG
    }

    val kayangel = adModel.kayangel
    var kayangelTG by mutableStateOf(kayangel.onePhase.totalGold + kayangel.twoPhase.totalGold + kayangel.threePhase.totalGold)

    fun kayangelTotal() {
        kayangelTG = kayangel.onePhase.totalGold + kayangel.twoPhase.totalGold + kayangel.threePhase.totalGold
    }

    val ivoryTower = adModel.ivoryTower
    var ivoryTowerTG by mutableStateOf(ivoryTower.onePhase.totalGold + ivoryTower.twoPhase.totalGold + ivoryTower.threePhase.totalGold + ivoryTower.fourPhase.totalGold)

    fun ivoryTowerTotal() {
        ivoryTowerTG = ivoryTower.onePhase.totalGold + ivoryTower.twoPhase.totalGold + ivoryTower.threePhase.totalGold + ivoryTower.fourPhase.totalGold
    }

}