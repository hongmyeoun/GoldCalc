package com.hongmyeoun.goldcalc.viewModel.homework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.homework.EpicRaidModel
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

class EpicRaidVM(val character: Character?): ViewModel() {
    private val epModel = EpicRaidModel(character)

    val behemoth = epModel.behemoth

    var totalGold by mutableStateOf(0)

    fun sumGold() {
        behemoth.totalGold()
        totalGold = behemoth.totalGold
    }

    init {
        sumGold()
    }

    var beheCheck by mutableStateOf(behemoth.isChecked)

    fun onBeheCheck() {
        beheCheck = !beheCheck
        behemoth.onShowChecked()
        sumGold()
    }

}