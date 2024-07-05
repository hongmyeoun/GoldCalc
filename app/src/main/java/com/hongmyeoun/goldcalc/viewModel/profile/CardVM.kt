package com.hongmyeoun.goldcalc.viewModel.profile

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.searchedInfo.card.CardEffects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CardVM: ViewModel() {
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

    fun onlySetOption(effect: CardEffects): String? {
        val cardInfo = effect.items.last().name
        val regex = Regex("""^[^\d]+""")
        return regex.find(cardInfo)?.value?.trim()
    }

    fun effect(effectName: String): String {
        val name = effectName.split(" ").last()

        return if (name.contains("각성")) {
            name.substringAfter("(").substringBefore("합계")
        } else {
            name
        }
    }

    fun awakePoint(awakeCount: Int): Int {
        return  when (awakeCount) {
            5 -> { R.drawable.img_profile_awake_filled5 }
            4 -> { R.drawable.img_profile_awake_filled4 }
            3 -> { R.drawable.img_profile_awake_filled3 }
            2 -> { R.drawable.img_profile_awake_filled2 }
            else -> { R.drawable.img_profile_awake_filled1 }
        }
    }

    fun cardBorderGrade(grade: String): Int {
        return  when (grade) {
            "일반" -> { R.drawable.img_card_grade_normal }
            "고급" -> { R.drawable.img_card_grade_uncommon }
            "희귀" -> { R.drawable.img_card_grade_rare }
            "영웅" -> { R.drawable.img_card_grade_epic }
            "전설" -> { R.drawable.img_card_grade_legendary }
            else -> { R.drawable.img_card_grade_relic }
        }
    }

    fun imageSizes(detail: Boolean, awakeCount: Int): Triple<Modifier, Modifier, Modifier> {
        val cardSize = if (!detail) Modifier.size(width = 53.6.dp, height = 80.dp) else Modifier.size(width = 110.5.dp, height = 164.93.dp)
        val awakeUnfilledSize = if (!detail) Modifier.size(width = 48.dp, height = 20.dp) else Modifier.size(width = 92.dp, height = 40.dp)
        val awakeFillSize = if (!detail) Modifier.size(width = (awakeCount * 9.6).dp, height = 20.dp) else Modifier.size(width = (awakeCount * 18.4).dp, height = 40.dp)

        return Triple(cardSize, awakeUnfilledSize, awakeFillSize)
    }
}