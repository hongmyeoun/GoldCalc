package com.hongmyeoun.goldcalc.viewModel.charDetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CombatStatDetailVM: ViewModel() {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog
    fun onDismissRequest() {
        _showDialog.value = false
    }

    fun onClicked() {
        _showDialog.value = true
    }

    // 물약 및 원정대 레벨로 추가된 스텟
    fun potionPlusStat(tooltip: List<String>): Int {
        // "물약 및 원정대 레벨" 포함된 부분과 숫자를 추출
        val regex = Regex("물약 및 원정대 레벨.*?<font color='#99ff99'>(\\d+)</font>")

        for (text in tooltip) {
            val matchResult = regex.find(text)
            if (matchResult != null) {
                return matchResult.groupValues[1].toInt() // 숫자만 추출하면 되므로
            }
        }

        return 0
    }

}