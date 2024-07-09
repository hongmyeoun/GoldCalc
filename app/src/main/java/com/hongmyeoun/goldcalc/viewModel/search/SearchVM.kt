package com.hongmyeoun.goldcalc.viewModel.search

import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.lostArkApi.APIRemote.getCharacter
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterInfo
import com.hongmyeoun.goldcalc.model.roomDB.searchHistory.SearchHistory
import com.hongmyeoun.goldcalc.model.roomDB.searchHistory.SearchHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository,
) : ViewModel() {
    private val _histories = MutableStateFlow<List<SearchHistory>>(emptyList())
    val histories: StateFlow<List<SearchHistory>> = _histories

    private fun getHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryRepository.getAll().collect {
                _histories.value = it
            }
        }
    }

    private val _characterName = MutableStateFlow("")
    val characterName: StateFlow<String> = _characterName

    fun onCharacterNameValueChange(newValue: String) {
        _characterName.value = newValue
    }

    fun characterClear() {
        _characterName.value = ""
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _characterList = MutableStateFlow<List<CharacterInfo>>(emptyList())
    val characterList: StateFlow<List<CharacterInfo>> = _characterList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private fun getCharacterList(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val (characterList, error) = getCharacter(_characterName.value)
            if (characterList != null) {
                _characterList.value = characterList
                _errorMessage.value = null
            } else {
                _characterList.value = emptyList()
                _errorMessage.value = error
            }
        }
    }

    private fun loadingTrue() {
        _isLoading.value = true
    }

    private fun loadingFalse() {
        _isLoading.value = false
    }

    fun onDone(context: Context) {
        loadingTrue()
        if (_characterName.value.isNotEmpty()) {
            getCharacterList(context)
            addHistory()
        }
        loadingFalse()
    }

    private val _isFocus = MutableStateFlow(false)
    val isFocus: StateFlow<Boolean> = _isFocus

    fun focusChange(focusState: Boolean) {
        _isFocus.value = focusState
    }

    fun unFocus() {
        _isFocus.value = false
    }

    private fun addHistory() {
        val currentSearch = _characterName.value

        val existingHistory = _histories.value.find { it.charName == currentSearch }

        if (existingHistory == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(Date())
                val history = SearchHistory(
                    charName = currentSearch,
                    timeStamp = timeStamp
                )
                searchHistoryRepository.insertAll(history)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(Date())
                val history = existingHistory.copy(
                    timeStamp = timeStamp
                )
                searchHistoryRepository.update(history)
            }
        }
    }

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog
    fun onDismissRequest() {
        _showDialog.value = false
    }

    fun showDialog(currentName: String) {
        onLongPress(currentName)
        _showDialog.value = true
    }

    private val _longPressName = MutableStateFlow("")
    val longPressName: StateFlow<String> = _longPressName

    private fun onLongPress(currentName: String) {
        _longPressName.value = currentName
    }

    fun deleteHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val searchHistory = _histories.value.find { it.charName == _longPressName.value }
            searchHistory?.let {
                searchHistoryRepository.delete(searchHistory)
            }
            _longPressName.value = ""
        }
        onDismissRequest()
    }

    init {
        getHistories()
    }

    @Composable
    fun animatedShape(isFocus: Boolean): RoundedCornerShape {
        val bottomStart by animateDpAsState(targetValue = if (isFocus) 0.dp else 16.dp, animationSpec = tween(durationMillis = 300), label = "")
        val bottomEnd by animateDpAsState(targetValue = if (isFocus) 0.dp else 16.dp, animationSpec = tween(durationMillis = 300), label = "")
        return RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = bottomStart, bottomEnd = bottomEnd)
    }
}