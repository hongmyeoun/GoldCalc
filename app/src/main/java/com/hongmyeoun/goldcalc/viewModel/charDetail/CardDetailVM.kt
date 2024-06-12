package com.hongmyeoun.goldcalc.viewModel.charDetail

import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.model.searchedInfo.card.CardEffects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CardDetailVM: ViewModel() {
    private val _isDetail = MutableStateFlow(false)
    val isDetail: StateFlow<Boolean> = _isDetail

    fun onDetailClicked() {
        _isDetail.value = !_isDetail.value
    }

    fun cardSetOption(effect: CardEffects): String? {
        val cardInfo = effect.items.last().name
        val regex = Regex("""(.*)\s(\d+세트)\s\((\d+)각성합계\)""")
        regex.find(cardInfo)?.let {
            val (setOption, setLevel, setAwakeCount) = it.destructured
            return "$setOption $setAwakeCount (${setLevel})"
        }
        return null
    }
}