package com.hongmyeoun.goldcalc.viewModel.main

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.model.roomDB.character.RaidPhaseInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog
    fun onDissmissRequest() {
        _showDialog.value = false
    }

    fun onClicked() {
        _showDialog.value = true
    }

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private fun mainScreenloading() {
        viewModelScope.launch {
            delay(1000)
            _isLoading.value = false
        }
    }

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.getAll().collect {
                _characters.value = it
                earnGold = it.fastSumBy { character -> character.earnGold } + it.fastSumBy { character -> character.plusGold.toInt() } - it.fastSumBy { character -> character.minusGold.toInt() }
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

    private var clickPressedTime = 0L

    // 두번 클릭해야 초기화가 진행됨
    fun onReset(snackbarHostState: SnackbarHostState) {
        val currentTime = System.currentTimeMillis()

        if (currentTime - clickPressedTime <= 2000L) {
            reset()
            doneSnackbar(
                snackbarHostState = snackbarHostState,
                text = "숙제가 초기화 되었습니다."
            )
            clickPressedTime = 0L // 초기화 후 한번더 초기화 할때 오류가 안나게 하기 위해 초기화
        } else {
            doneSnackbar(
                snackbarHostState = snackbarHostState,
                text = "한 번 더 누르시면 숙제가 초기화 됩니다."
            )
        }
        clickPressedTime = currentTime
    }

    private fun reset() {
        viewModelScope.launch(Dispatchers.IO) {
            val resetData = _characters.value.map {
                it.copy(
                    earnGold = 0,
                    raidPhaseInfo = RaidPhaseInfo()
                )
            }
            characterRepository.updateAll(resetData)
            _isLoading.value = true
            mainScreenloading()
        }
    }

    private fun doneSnackbar(snackbarHostState: SnackbarHostState, text: String) {
        viewModelScope.launch {
            val job = launch {
                snackbarHostState.showSnackbar(message = text)
            }
            delay(2000L)
            job.cancel()
        }
    }

    init {
        getCharacters()
        mainScreenloading()
    }
}
