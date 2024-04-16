package com.hongmyeoun.goldcalc.viewModel.charDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharDetailVM @Inject constructor(
    private val characterRepository: CharacterRepository
): ViewModel() {
    var isSaved by mutableStateOf(false)

    fun isSavedName(charName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedNames = withContext(Dispatchers.IO) { characterRepository.getNames() }
            isSaved = savedNames.contains(charName)
        }
    }

    fun saveCharacter(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.insertAll(character)
        }
    }
}