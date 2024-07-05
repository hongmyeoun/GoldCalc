package com.hongmyeoun.goldcalc.view.profile.content.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.model.searchedInfo.card.CardEffects
import com.hongmyeoun.goldcalc.model.searchedInfo.card.Cards
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleBoldWhite12
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.viewModel.profile.CardVM

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Detail(
    characterCardsEffects: List<CardEffects>?,
    cardList: List<Cards>,
    viewModel: CardVM
) {
    // 카드 이미지
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        maxItemsInEachRow = 3
    ) {
        cardList.forEach { card ->
            CardImage(
                grade = card.grade,
                icon = card.icon,
                name = card.name,
                awakeCount = card.awakeCount,
                viewModel = viewModel,
                detail = true
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))

    // 카드 세트 옵션 효과
    characterCardsEffects?.let { cardEffects ->
        cardEffects.forEach { effect ->
            val setOption = viewModel.onlySetOption(effect)
            setOption?.let { setOpt ->
                Column(
                    modifier = Modifier
                        .background(ImageBG, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // 카드 세트 옵션 이름(ex: 남겨진 바람의 절벽)
                    Text(
                        text = setOpt,
                        style = titleBoldWhite12()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    effect.items.forEach {
                        val name = viewModel.effect(it.name)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // 세트 혹은 각성 단계
                            TextChip(
                                text = name, // "3세트" or "18각성")
                                customBGColor = LightGrayBG,
                                borderless = true,
                                fixedWidth = true,
                                customWidthSize = 50.dp,
                                customRoundedCornerSize = 8.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            // 세트 옵션 혹은 각성 옵션 설명
                            Text(
                                text = it.description, // "가디언 토벌 시 가디언에게 받는 피해 7.5% 감소"
                                style = normalTextStyle()
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}