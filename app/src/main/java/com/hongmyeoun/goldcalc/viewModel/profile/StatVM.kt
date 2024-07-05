package com.hongmyeoun.goldcalc.viewModel.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StatVM: ViewModel() {
    private val _showDefaultDialog = MutableStateFlow(false)
    val showDefaultDialog: StateFlow<Boolean> = _showDefaultDialog
    fun onDismissRequest() {
        _showDefaultDialog.value = false
        _showCombatDialog.value = false
    }

    fun onDefaultClicked() {
        _showDefaultDialog.value = true
    }

    private val _showCombatDialog = MutableStateFlow(false)
    val showCombatDialog: StateFlow<Boolean> = _showCombatDialog

    fun onCombatClicked() {
        _showCombatDialog.value = true
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

    fun removeHTMLTags(htmlStr: String): String {
        return htmlStr.replace(Regex("<.*?>"), "").trim()
    }
}