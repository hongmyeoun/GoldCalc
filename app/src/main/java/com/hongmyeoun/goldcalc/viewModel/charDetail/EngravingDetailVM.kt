package com.hongmyeoun.goldcalc.viewModel.charDetail

import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.searchedInfo.engravings.SkillEngravings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EngravingDetailVM: ViewModel() {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog
    fun onDismissRequest() {
        _engravingName.value = ""
        _showDialog.value = false
    }

    fun onClicked(engravingName: String) {
        _engravingName.value = engravingName
        _showDialog.value = true
    }

    private val _engravingName = MutableStateFlow("")

    fun getEngraving(skillEngravings: List<SkillEngravings>): SkillEngravings? {
        return skillEngravings.find { it.name == _engravingName.value }
    }
}