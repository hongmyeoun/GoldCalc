package com.hongmyeoun.goldcalc.viewModel.setting

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.model.roomDB.character.RaidPhaseInfo
import com.hongmyeoun.goldcalc.model.setting.SettingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    private val _reorderPage = MutableStateFlow(false)
    val reorderPage: StateFlow<Boolean> = _reorderPage

    fun openRerderPage() {
        _reorderPage.value = true
    }

    fun closeRerderPage() {
        _reorderPage.value = false
    }

    private val _newList = MutableStateFlow<List<Character>>(emptyList())

    fun saveReorderList(reorderedList: List<Character>) {
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

    fun onSave(snackbarHostState: SnackbarHostState,) {
        closeRerderPage()
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
        doneSnackbar(
            snackbarHostState = snackbarHostState,
            text = "변경사항이 저장되었습니다."
        )
    }

    fun doneSnackbar(
        snackbarHostState: SnackbarHostState,
        text: String,
    ) {
        viewModelScope.launch {
            val job = launch {
                snackbarHostState.showSnackbar(message = text)
            }
            delay(2000L)
            job.cancel()
        }
    }

    private val _cacheSize = MutableStateFlow(0L)
    val cacheSize: StateFlow<Long> = _cacheSize

    fun getCacheSize(context: Context) {
        _cacheSize.value = SettingModel.getCacheSize(context)
    }

    fun cacheDelete(context: Context, snackbarHostState: SnackbarHostState,) {
        SettingModel.clearAppCache(context) {
            doneSnackbar(
                snackbarHostState = snackbarHostState,
                text = "캐쉬가 정리되었습니다."
            )
            _cacheSize.value = SettingModel.getCacheSize(context)
        }
    }

    init {
        getCharacters()
    }
}