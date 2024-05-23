package com.hongmyeoun.goldcalc.viewModel.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.lostArkApi.APIRemote.getCharacter
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchVM : ViewModel() {
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
            val (characterList, error) = getCharacter(context, _characterName.value)
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
        }
        loadingFalse()
        searchedTrue()
    }

    private val _isSearch = MutableStateFlow(false)
    val isSearch: StateFlow<Boolean> = _isSearch

    private val _tempCharName = MutableStateFlow("")
    val tempCharName: StateFlow<String> = _tempCharName

    private fun searchedTrue() {
        _isSearch.value = true
        _tempCharName.value = _characterName.value
    }
}