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

    fun removeHTMLTags(htmlStr: String): String {
        return htmlStr.replace(Regex("<.*?>"), "").trim()
    }
}