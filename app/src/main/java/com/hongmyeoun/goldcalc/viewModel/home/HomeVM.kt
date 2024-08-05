package com.hongmyeoun.goldcalc.viewModel.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.constants.viewConst.SnackbarMessage
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
import kotlin.math.absoluteValue

@HiltViewModel
class HomeVM @Inject constructor(
    val characterRepository: CharacterRepository,
): ViewModel() {
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog
    fun onDismissRequest() {
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

    private val _totalWeeklyEarnGold = MutableStateFlow(0)
    val totalWeeklyEarnGold: StateFlow<Int> = _totalWeeklyEarnGold

    private val _remainGold = MutableStateFlow(0)
    val remainGold: StateFlow<Int> = _remainGold

    private val _maxGold = MutableStateFlow(0)
    val maxGold: StateFlow<Int> = _maxGold

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.getAll().collect {
                _characters.value = it
                _totalWeeklyEarnGold.value = it.fastSumBy { character -> character.earnGold } + it.fastSumBy { character -> character.plusGold.toInt() } - it.fastSumBy { character -> character.minusGold.toInt() }
                initProgressBar(it)
            }
        }
    }

    private fun initProgressBar(characterList: List<Character>) {
        _maxGold.value = characterList.fastSumBy { it.weeklyGold }
        _remainGold.value = _maxGold.value - _totalWeeklyEarnGold.value

        val extraGold = characterList.fastSumBy { character -> character.plusGold.toInt() } - characterList.fastSumBy { character -> character.minusGold.toInt() }
        val totalGold = _maxGold.value + extraGold.absoluteValue

        _progressPercentage.value = if (_maxGold.value != 0) 1 - (_remainGold.value.toFloat() / totalGold) else 0.0f
    }

    private val _progressPercentage = MutableStateFlow(0.0f)
    val progressPercentage: StateFlow<Float> = _progressPercentage

    private var clickPressedTime = 0L

    // 두번 클릭해야 초기화가 진행됨
    fun onReset(snackbarHostState: SnackbarHostState) {
        val currentTime = System.currentTimeMillis()

        if (currentTime - clickPressedTime <= 2000L) {
            reset()
            doneSnackbar(
                snackbarHostState = snackbarHostState,
                text = SnackbarMessage.HW_INIT,
                delay = 1500L
            )
            clickPressedTime = 0L // 초기화 후 한번더 초기화 할때 오류가 안나게 하기 위해 초기화
        } else {
            doneSnackbar(
                snackbarHostState = snackbarHostState,
                text = SnackbarMessage.HW_INIT_WARN,
                delay = 750L
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

    private fun doneSnackbar(snackbarHostState: SnackbarHostState, text: String, delay: Long = 2000L) {
        viewModelScope.launch {
            val job = launch {
                snackbarHostState.showSnackbar(message = text)
            }
            delay(delay)
            job.cancel()
        }
    }

    init {
        getCharacters()
        mainScreenloading()
    }
}
