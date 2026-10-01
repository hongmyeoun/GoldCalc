package com.hongmyeoun.goldcalc.viewModel.homework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.homework.EventRaidModel
import com.hongmyeoun.goldcalc.model.roomDB.character.Character

class EventRaidVM(val character: Character?): ViewModel() {
    private val eventModel = EventRaidModel(character)

    val event = eventModel.event

    var totalGold by mutableStateOf(0)

    fun sumGold() {
        event.totalGold()
        totalGold = event.totalGold
    }

    init {
        sumGold()
    }

    var eventCheck by mutableStateOf(event.isChecked)

    fun onEventCheck() {
        eventCheck = !eventCheck
        event.onShowChecked()
        sumGold()
    }
}