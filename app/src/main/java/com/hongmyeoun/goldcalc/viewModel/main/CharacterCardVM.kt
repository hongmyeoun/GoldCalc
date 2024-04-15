package com.hongmyeoun.goldcalc.viewModel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.roomDB.Character
import com.hongmyeoun.goldcalc.model.roomDB.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterCardVM @Inject constructor(
    private val characterRepository: CharacterRepository
): ViewModel() {
    val characters: StateFlow<List<Character>> = characterRepository.getAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun delete(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.delete(character)
        }
    }
}