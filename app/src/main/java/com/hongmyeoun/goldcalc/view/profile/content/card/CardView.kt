package com.hongmyeoun.goldcalc.view.profile.content.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hongmyeoun.goldcalc.model.searchedInfo.card.CardEffects
import com.hongmyeoun.goldcalc.model.searchedInfo.card.Cards
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.profile.Title
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.viewModel.charDetail.CardDetailVM
import com.hongmyeoun.goldcalc.viewModel.charDetail.CharDetailVM

@Composable
fun Card(
    viewModel: CharDetailVM
) {
    // 카드
    val cards by viewModel.cards.collectAsState()

    // 카드 효과
    val cardsEffects by viewModel.cardsEffects.collectAsState()

    cards?.let { cardList ->
        CardView(cardsEffects, cardList)
    }
}

@Composable
fun CardView(
    characterCardsEffects: List<CardEffects>?,
    cardList: List<Cards>,
    viewModel: CardDetailVM = viewModel()
) {
    val isDetail by viewModel.isDetail.collectAsState()

    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Title(
            title = "카드",
            onClick = { viewModel.onDetailClicked() }
        )

        SetOption(
            characterCardsEffects = characterCardsEffects,
            viewModel = viewModel
        )

        if (isDetail) {
            Detail(
                characterCardsEffects = characterCardsEffects,
                cardList = cardList,
                viewModel = viewModel
            )
        } else {
            Simple(
                cardList = cardList,
                viewModel = viewModel
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

// 카드 세트 효과 (ex: 남겨진 바람의 절벽 30(6세트))
@Composable
fun SetOption(
    characterCardsEffects: List<CardEffects>?,
    viewModel: CardDetailVM,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        characterCardsEffects?.let { cardEffects ->
            cardEffects.forEach { effect ->
                val setOption = viewModel.cardSetOption(effect)
                setOption?.let {
                    TextChip(text = it)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}