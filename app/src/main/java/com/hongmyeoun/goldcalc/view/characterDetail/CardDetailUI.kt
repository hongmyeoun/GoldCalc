package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.viewModel.charDetail.CardDetailVM

@OptIn(ExperimentalLayoutApi::class)
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

        if (isDetail) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
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

            characterCardsEffects?.let { cardEffects ->
                cardEffects.forEach { effects ->
                    effects.items.forEach {
                        Column(
                            modifier = Modifier
                                .background(ImageBG, RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "부르는 소리 있도다",
                                style = titleBoldWhite12()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextChip(
                                    text = "3세트",
                                    customBG = LightGrayBG,
                                    borderless = true,
                                    fixedWidth = true,
                                    customWidthSize = 50.dp,
                                    customRoundedCornerSize = 8.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "가디언 토벌 시 가디언에게 받는 피해 7.5% 감소",
                                    style = normalTextStyle()
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextChip(
                                    text = "15각성",
                                    customBG = LightGrayBG,
                                    borderless = true,
                                    fixedWidth = true,
                                    customWidthSize = 50.dp,
                                    customRoundedCornerSize = 8.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "가디언 토벌 시 가디언에게 받는 피해 7.5% 감소",
                                    style = normalTextStyle()
                                )
                            }
                        }
                    }
                }
            }

        } else {
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
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CardImage(
    grade: String,
    icon: String,
    awakeCount: Int,
    viewModel: CardDetailVM,
    name: String = "",
    detail: Boolean = false
) {
    val cardBorder = viewModel.cardBorderGrade(grade)
    val awake = viewModel.awakePoint(awakeCount)

    val cardSize = if (!detail) Modifier.size(width = 53.6.dp, height = 80.dp) else Modifier.size(width = 110.5.dp, height = 164.93.dp)
    val awakeUnfilledSize = if (!detail) Modifier.size(width = 48.dp, height = 20.dp) else Modifier.size(width = 92.dp, height = 40.dp)
    val awakeFillSize =
        if (!detail) Modifier.size(width = (awakeCount * 9.6).dp, height = 20.dp) else Modifier.size(width = (awakeCount * 18.4).dp, height = 40.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            GlideImage(
                modifier = Modifier
                    .then(cardSize)
                    .padding(start = 1.dp, end = 1.dp),
                model = icon,
                contentDescription = "카드 이미지"
            )
            GlideImage(
                modifier = cardSize,
                loading = placeholder(painterResource(id = cardBorder)),
                model = cardBorder,
                contentDescription = "카드 테두리"
            )
            Box(
                modifier = cardSize,
                contentAlignment = Alignment.BottomCenter
            ) {
                // 활성화 안된거
                Box {
                    GlideImage(
                        modifier = awakeUnfilledSize,
                        loading = placeholder(painterResource(id = R.drawable.img_profile_awake_unfilled)),
                        model = R.drawable.img_profile_awake_unfilled,
                        contentDescription = "빈슬롯"
                    )
                    // 활성화 된거
                    GlideImage(
                        modifier = awakeFillSize,
                        loading = placeholder(painterResource(id = awake)),
                        model = awake,
                        contentDescription = "활성화"
                    )
                }
            }
        }
        if (detail) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = name,
                style = titleBoldWhite12()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoldCalcTheme {
        Column(
            modifier = Modifier
                .background(ImageBG, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "부르는 소리 있도다",
                style = titleBoldWhite12()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextChip(
                    text = "3세트",
                    customBG = LightGrayBG,
                    borderless = true,
                    fixedWidth = true,
                    customWidthSize = 50.dp,
                    customRoundedCornerSize = 8.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "가디언 토벌 시 가디언에게 받는 피해 7.5% 감소",
                    style = normalTextStyle()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextChip(
                    text = "15각성",
                    customBG = LightGrayBG,
                    borderless = true,
                    fixedWidth = true,
                    customWidthSize = 50.dp,
                    customRoundedCornerSize = 8.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "가디언 토벌 시 가디언에게 받는 피해 7.5% 감소",
                    style = normalTextStyle()
                )
            }
        }
    }
}