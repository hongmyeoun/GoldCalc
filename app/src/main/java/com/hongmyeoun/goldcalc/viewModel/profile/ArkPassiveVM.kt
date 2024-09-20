package com.hongmyeoun.goldcalc.viewModel.profile

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassiveNode
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveEnlightenment
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveEvolution
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveLeap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArkPassiveVM : ViewModel() {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _arkPassiveNodeName = MutableStateFlow("")

    fun onDismissRequest() {
        _arkPassiveNodeName.value = ""
        _showDialog.value = false
    }

    fun onClicked(name: String) {
        _arkPassiveNodeName.value = name
        _showDialog.value = true
    }

    fun getArkPassive(arkPassiveNodes: List<ArkPassiveNode>): ArkPassiveNode? {
        return arkPassiveNodes.find { it.name == _arkPassiveNodeName.value }
    }

    fun textColor(type: String): Color {
        return when (type) {
            "진화" -> ArkPassiveEvolution
            "깨달음" -> ArkPassiveEnlightenment
            else -> ArkPassiveLeap
        }
    }

    fun arkPassiveIcon(type: String) : Int {
        return when (type) {
            "진화" -> R.drawable.ico_arkpassive_evolution
            "깨달음" -> R.drawable.ico_arkpassive_enlightenment
            else -> R.drawable.ico_arkpassive_leap
        }
    }
}