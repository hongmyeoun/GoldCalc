package com.hongmyeoun.goldcalc.viewModel.setting

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.model.roomDB.character.RaidPhaseInfo
import com.hongmyeoun.goldcalc.model.roomDB.searchHistory.SearchHistory
import com.hongmyeoun.goldcalc.model.roomDB.searchHistory.SearchHistoryRepository
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
    private val characterRepository: CharacterRepository,
    private val searchHistoryRepository: SearchHistoryRepository,
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

    private val _histories = MutableStateFlow<List<SearchHistory>>(emptyList())

    private fun getHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryRepository.getAll().collect {
                _histories.value = it
            }
        }
    }

    private val _showResetDialog = MutableStateFlow(false)
    val showResetDialog: StateFlow<Boolean> = _showResetDialog

    fun showResetDialog() {
        _showResetDialog.value = true
    }

    private val _showDeleteCharListDialog = MutableStateFlow(false)
    val showDeleteCharListDialog: StateFlow<Boolean> = _showDeleteCharListDialog

    fun showDeleteCharListDialog() {
        _showDeleteCharListDialog.value = true
    }

    private val _showDeleteHistoryDialog = MutableStateFlow(false)
    val showDeleteHistoryDialog: StateFlow<Boolean> = _showDeleteHistoryDialog

    fun showDeleteHistoryDialog() {
        _showDeleteHistoryDialog.value = true
    }

    fun onDissmissRequest() {
        _showResetDialog.value = false
        _showDeleteCharListDialog.value = false
        _showDeleteHistoryDialog.value = false
    }

    private val _reorderPage = MutableStateFlow(false)
    val reorderPage: StateFlow<Boolean> = _reorderPage

    fun openReorderPage() {
        _reorderPage.value = true
    }

    fun closeReorderPage() {
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

    fun onDeleteAllCharList() {
        viewModelScope.launch(Dispatchers.IO) {
            _characters.value.forEach {
                characterRepository.delete(it)
            }
        }
        onDissmissRequest()
    }

    fun onDeleteAllHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            _histories.value.forEach {
                searchHistoryRepository.delete(it)
            }
        }
        onDissmissRequest()
    }

    fun onSave(snackbarHostState: SnackbarHostState,) {
        closeReorderPage()
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
                text = "캐시가 정리되었습니다."
            )
            _cacheSize.value = SettingModel.getCacheSize(context)
        }
    }

    init {
        getCharacters()
        getHistories()
    }
}