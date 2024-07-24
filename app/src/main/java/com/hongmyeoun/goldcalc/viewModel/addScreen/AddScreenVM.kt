package com.hongmyeoun.goldcalc.viewModel.addScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.constants.ClassName
import com.hongmyeoun.goldcalc.model.constants.viewConst.Profile
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddScreenVM @Inject constructor(
    val characterRepository: CharacterRepository
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

    init {
        getCharacters()
    }

    private val _isDuplicate = MutableStateFlow(false)
    val isDuplicate: StateFlow<Boolean> = _isDuplicate

    private val _isDuplicateCheck = MutableStateFlow(false)
    val isDuplicateCheck: StateFlow<Boolean> = _isDuplicateCheck

    private val _finalNickname = MutableStateFlow("")
    val finalNickname: StateFlow<String> = _finalNickname

    fun checkDuplication(nickname: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedNames = withContext(Dispatchers.IO) { characterRepository.getNames() }
            _isDuplicate.value = savedNames.contains(nickname)
            _finalNickname.value = _nickname.value
        }
        _isDuplicateCheck.value = true
    }

    private val _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> = _nickname

    fun nicknameValueChange(newValue: String) {
        val maxLength = 12

        if (newValue.length <= maxLength) {
            _nickname.value = newValue
        }
    }

    private val _itemLevel = MutableStateFlow("")
    val itemLevel: StateFlow<String> = _itemLevel

    fun itemLevelValueChange(newValue: String) {
        val maxLength = 7
        val maxLevel = 1714.17

        if (newValue.length <= maxLength && newValue.toFloatOrNull() != null && newValue.toFloat() <= maxLevel + 0.01f) {
            _itemLevel.value = newValue
        } else if (newValue.toFloatOrNull() == null) {
            _itemLevel.value = ""
        }
    }

    private val _serverDropdownExpanded = MutableStateFlow(false)
    val serverDropdownExpanded: StateFlow<Boolean> = _serverDropdownExpanded

    val serverName = Profile.SERVER_LIST

    private val _serverSelect = MutableStateFlow(serverName[0])
    val serverSelect: StateFlow<String> = _serverSelect

    fun onServerDropdownClicked() {
        _serverDropdownExpanded.value = true
    }

    fun onServerDropdownDismiss() {
        _serverDropdownExpanded.value = false
    }

    fun onServerSelected(selectServer: String) {
        _serverSelect.value = selectServer
        onServerDropdownDismiss()
    }

    private val _classDropdownExpanded = MutableStateFlow(false)
    val classDropdownExpanded: StateFlow<Boolean> = _classDropdownExpanded

    val className = ClassName.ClassLists.CHARACTER_LIST

    private val _classSelect = MutableStateFlow(className[0])
    val classSelect: StateFlow<String> = _classSelect

    fun onClassDropdownClicked() {
        _classDropdownExpanded.value = true
    }

    fun onClassDropdownDismiss() {
        _classDropdownExpanded.value = false
    }

    fun onClassSelected(classSelect: String) {
        _classSelect.value = classSelect
        onClassDropdownDismiss()
    }

    fun confirmEnable(isDuplicate: Boolean, nowNickname: String): Boolean {
        return !isDuplicate && (_finalNickname.value == nowNickname) && nowNickname.isNotEmpty()
    }

    fun confirm() {
        viewModelScope.launch(Dispatchers.IO) {
            val newCharacter = Character(
                name = _nickname.value,
                itemLevel = _itemLevel.value.ifEmpty { "0.00" },
                serverName = _serverSelect.value,
                className = _classSelect.value
            )
            characterRepository.insertAll(newCharacter)
        }
    }
}