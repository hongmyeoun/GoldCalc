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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.searchedInfo.card.CardEffects
import com.hongmyeoun.goldcalc.model.searchedInfo.card.Cards
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

            // 카드 세트 효과 (ex: 남겨진 바람의 절벽 30(6세트))
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
        }
        Spacer(modifier = Modifier.height(4.dp))

        if (isDetail) {
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
    val (cardSize, awakeUnfilledSize, awakeFillSize) = viewModel.imageSizes(detail, awakeCount)

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
                        model = R.drawable.img_profile_awake_unfilled,
                        contentDescription = "빈슬롯"
                    )
                    // 활성화 된거
                    GlideImage(
                        modifier = awakeFillSize,
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