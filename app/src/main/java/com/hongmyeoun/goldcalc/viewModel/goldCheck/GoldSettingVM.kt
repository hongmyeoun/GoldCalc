package com.hongmyeoun.goldcalc.viewModel.goldCheck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.roomDB.Character
import com.hongmyeoun.goldcalc.model.roomDB.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GoldSettingVM(private val repository: CharacterRepository, charName: String): ViewModel() {
    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    init {
        getCharacter(charName)
    }

    private fun getCharacter(charName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacterByName(charName).collect { character ->
                _character.value = character
            }
        }
    }

    fun onDoneClick() {
        val update = _character.value?.copy(weeklyGold = totalGold)
        update?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.update(update)
            }
        }
    }

    var plusGold by mutableStateOf("0")
    fun plusGoldValue(newValue: String){
        plusGold = newValue.filter { it.isDigit() }
    }

    var minusGold by mutableStateOf("0")
    fun minusGoldValue(newValue: String){
        minusGold = newValue.filter { it.isDigit() }
    }

    var totalGold by mutableStateOf(0)
    private fun calcTotalGold(cb: Int, ad: Int, kz: Int, ep: Int): Int {
        val plusGoldInt = plusGold.toIntOrNull()?:0
        val minusGoldInt = minusGold.toIntOrNull()?:0
        totalGold = cb + ad + kz + ep + plusGoldInt - minusGoldInt
        return totalGold
    }

    fun updateTotalGold(cb: Int, ad: Int, kz: Int, ep: Int) {
        calcTotalGold(cb, ad, kz, ep)
    }

    var expanded by mutableStateOf(false)
    fun expand() {
        expanded = !expanded
    }

    var selectedTab by mutableStateOf("군단장")
    fun moveCommandRaid() {
        selectedTab = "군단장"
    }
    fun moveAbyssDungeon() {
        selectedTab = "어비스 던전"
    }
    fun moveKazeRaid() {
        selectedTab = "카제로스"
    }
    fun moveEpicRaid() {
        selectedTab = "에픽"
    }
    fun moveETC() {
        selectedTab = "기타"
    }
}