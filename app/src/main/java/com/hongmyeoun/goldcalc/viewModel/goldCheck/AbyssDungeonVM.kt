package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.goldCheck.AbyssDungeonModel
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

class AbyssDungeonVM(val character: Character?): ViewModel() {
    private val adModel = AbyssDungeonModel(character)

    val kayangel = adModel.kayangel
    val ivoryTower = adModel.ivoryTower

    var totalGold by mutableStateOf(0)

    fun sumGold(){
        kayangel.totalGold()
        ivoryTower.totalGold()
        totalGold = kayangel.totalGold + ivoryTower.totalGold
    }

    init {
        sumGold()
    }

    var kayangelCheck by mutableStateOf(kayangel.isChecked)
    var ivoryCheck by mutableStateOf(ivoryTower.isChecked)

    fun onKayangelCheck() {
        kayangelCheck = !kayangelCheck
        kayangel.onShowChecked()
        sumGold()
    }

    fun onIvoryCheck() {
        ivoryCheck = !ivoryCheck
        ivoryTower.onShowChecked()
        sumGold()
    }

}