package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.searchedInfo.card.CardEffects
import com.hongmyeoun.goldcalc.model.searchedInfo.card.Cards
import com.hongmyeoun.goldcalc.ui.theme.GoldCalcTheme
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.viewModel.charDetail.CardDetailVM

@Composable
fun CardDetailUI(
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
        Column(
            modifier = Modifier.clickable { viewModel.onDetailClicked() }
        ) {

            Text(
                text = "카드",
                style = titleTextStyle()
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                characterCardsEffects?.let { cardEffects ->
                    cardEffects.forEach { effect ->
                        val setOption = viewModel.cardSetOption(effect)
                        setOption?.let {
                            TextChip(text = it)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

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
    Spacer(modifier = Modifier.height(8.dp))
}

//@OptIn(ExperimentalGlideComposeApi::class)
//@Composable
//private fun CardImage(grade: String, icon: Int, awakeCount: Int) {
//    val cardBorder = cardBorderGrade(grade)
//    val awake = awakePoint(awakeCount)
//
//    Box {
//        GlideImage(
//            modifier = Modifier
//                .size(width = 53.6.dp, height = 80.dp)
//                .padding(start = 1.dp, end = 1.dp),
//            loading = placeholder(painterResource(id = icon)),
//            model = icon,
//            contentDescription = "카드 이미지"
//        )
//        GlideImage(
//            modifier = Modifier.size(width = 53.6.dp, height = 80.dp),
//            loading = placeholder(painterResource(id = cardBorder)),
//            model = cardBorder,
//            contentDescription = "카드 테두리"
//        )
//        Box(
//            modifier = Modifier.size(width = 53.6.dp, height = 80.dp),
//            contentAlignment = Alignment.BottomCenter
//        ) {
//            // 활성화 안된거
//            Box {
//                GlideImage(
//                    modifier = Modifier.size(width = 48.dp, height = 20.dp),
//                    loading = placeholder(painterResource(id = R.drawable.img_profile_awake_unfilled)),
//                    model = R.drawable.img_profile_awake_unfilled,
//                    contentDescription = "빈슬롯"
//                )
//                // 활성화 된거
//                GlideImage(
//                    modifier = Modifier.size(width = (awakeCount * 1.2 * 8).dp, height = 20.dp),
//                    loading = placeholder(painterResource(id = awake)),
//                    model = awake,
//                    contentDescription = "활성화"
//                )
//            }
//        }
//    }
//
//}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CardImage(grade: String, icon: String, awakeCount: Int, viewModel: CardDetailVM) {
    val cardBorder = viewModel.cardBorderGrade(grade)
    val awake = viewModel.awakePoint(awakeCount)

    Box {
        GlideImage(
            modifier = Modifier
                .size(width = 53.6.dp, height = 80.dp)
                .padding(start = 1.dp, end = 1.dp),
            model = icon,
            contentDescription = "카드 이미지"
        )
        GlideImage(
            modifier = Modifier.size(width = 53.6.dp, height = 80.dp),
            loading = placeholder(painterResource(id = cardBorder)),
            model = cardBorder,
            contentDescription = "카드 테두리"
        )
        Box(
            modifier = Modifier.size(width = 53.6.dp, height = 80.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            // 활성화 안된거
            Box {
                GlideImage(
                    modifier = Modifier.size(width = 48.dp, height = 20.dp),
                    loading = placeholder(painterResource(id = R.drawable.img_profile_awake_unfilled)),
                    model = R.drawable.img_profile_awake_unfilled,
                    contentDescription = "빈슬롯"
                )
                // 활성화 된거
                GlideImage(
                    modifier = Modifier.size(width = (awakeCount * 1.2 * 8).dp, height = 20.dp),
                    loading = placeholder(painterResource(id = awake)),
                    model = awake,
                    contentDescription = "활성화"
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoldCalcTheme {
//        CardImage("전설", R.drawable.card_legend_00_0, 4)
    }
}