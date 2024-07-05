package com.hongmyeoun.goldcalc.view.profile.content.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.model.profile.card.Cards
import com.hongmyeoun.goldcalc.viewModel.profile.CardVM

@Composable
fun Simple(
    cardList: List<Cards>,
    viewModel: CardVM
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        cardList.forEach { card ->
            CardImage(
                grade = card.grade,
                icon = card.icon,
                awakeCount = card.awakeCount,
                viewModel = viewModel
            )
        }
    }
}