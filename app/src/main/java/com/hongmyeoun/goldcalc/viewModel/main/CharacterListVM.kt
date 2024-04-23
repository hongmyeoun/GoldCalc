package com.hongmyeoun.goldcalc.viewModel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListVM @Inject constructor(
    val characterRepository: CharacterRepository,
): ViewModel() {
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.getAll().collect {
                _characters.value = it
                earnGold = it.fastSumBy { character -> character.earnGold }
                initProgressBar(it)
                remainGold = maxGold - earnGold
            }
        }
    }

    private fun initProgressBar(characterList: List<Character>) {
        maxGold = characterList.fastSumBy { it.weeklyGold }
        progressPercentage = if (maxGold != 0) earnGold.toFloat() / maxGold else 0.0f
    }

    var maxGold by mutableStateOf(0)
    var progressPercentage by mutableStateOf(0.0f)
        private set

    var earnGold by mutableStateOf(0)
    var remainGold by mutableStateOf(0)


    init {
        getCharacters()
    }

    fun delete(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.delete(character)
        }
    }

}
