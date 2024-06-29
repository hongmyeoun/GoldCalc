package com.hongmyeoun.goldcalc.viewModel.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.model.roomDB.character.RaidPhaseInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingVM @Inject constructor(
    private val characterRepository: CharacterRepository
): ViewModel() {
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.getAll().collect {
                _characters.value = it
            }
        }
    }

    private val _showResetDialog = MutableStateFlow(false)
    val showResetDialog: StateFlow<Boolean> = _showResetDialog

    fun showResetDailog() {
        _showResetDialog.value = true
    }

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog

    fun showDeleteDialog() {
        _showDeleteDialog.value = true
    }

    fun onDissmissRequest() {
        _showResetDialog.value = false
        _showDeleteDialog.value = false
    }

    private val _editOrderPage = MutableStateFlow(false)
    val editOrderPage: StateFlow<Boolean> = _editOrderPage

    fun openEditOrderPage() {
        _editOrderPage.value = true
    }

    private fun closeEditOrderPage() {
        _editOrderPage.value = false
    }

    private val _newList = MutableStateFlow<List<Character>>(emptyList())

    fun dragStopSave(reorderedList: List<Character>) {
        if (reorderedList != _characters.value) {
            _newList.value = reorderedList
        }
    }

    fun onHomeworkReset() {
        viewModelScope.launch(Dispatchers.IO) {
            val resetData = _characters.value.map {
                it.copy(
                    earnGold = 0,
                    raidPhaseInfo = RaidPhaseInfo()
                )
            }
            characterRepository.updateAll(resetData)
        }
        onDissmissRequest()
    }

    fun onDeleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _characters.value.forEach {
                characterRepository.delete(it)
            }
        }
        onDissmissRequest()
    }

    fun onSave() {
        closeEditOrderPage()
        if (_newList.value.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                _characters.value.forEach {
                    characterRepository.delete(it)
                }
                _newList.value.forEach {
                    characterRepository.insertAll(it)
                }
            }
        }
    }

    init {
        getCharacters()
    }
}