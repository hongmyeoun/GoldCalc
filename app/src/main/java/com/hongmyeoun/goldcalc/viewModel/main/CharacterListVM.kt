package com.hongmyeoun.goldcalc.viewModel.main

import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListVM @Inject constructor(
    val characterRepository: CharacterRepository,
): ViewModel() {
    val characters: StateFlow<List<Character>> = characterRepository.getAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())



    fun calcProgressPercentage(characterList: List<Character>): Float {
        return if (calcWeeklyGold(characterList) != 0) calcEarnGold(characterList).toFloat() / calcWeeklyGold(characterList) else 0.0f
    }

    fun calcWeeklyGold(characterList: List<Character>): Int {
        return characterList.fastSumBy { it.weeklyGold }
    }

    fun calcEarnGold(characterList: List<Character>): Int {
        return characterList.fastSumBy { it.earnGold }
    }

    fun calcRemainGold(characterList: List<Character>): Int {
        return calcWeeklyGold(characterList) - calcEarnGold(characterList)
    }

    fun delete(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.delete(character)
        }
    }

}
