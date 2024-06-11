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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.searchedInfo.card.CardEffects
import com.hongmyeoun.goldcalc.model.searchedInfo.card.Cards
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG

@Composable
fun CardDetailUI(
    characterCardsEffects: List<CardEffects>?,
    cardList: List<Cards>
) {
    var isDetail by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.clickable { isDetail = !isDetail }
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
                        val cardInfo = effect.items.last().name
                        val regex = Regex("""(.*)\s(\d+세트)\s\((\d+)각성합계\)""")
                        regex.find(cardInfo)?.let {
                            val (setOption, setLevel, setAwakeCount) = it.destructured
                            TextChip(text = "$setOption $setAwakeCount (${setLevel})")
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
                    awakeCount = card.awakeCount
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CardImage(grade: String, icon: String, awakeCount: Int) {
    val horizontalBias = when (grade) {
        "일반" -> {
            -1f
        }

        "고급" -> {
            -0.6f
        }

        "희귀" -> {
            -0.2f
        }

        "영웅" -> {
            0.2f
        }

        "전설" -> {
            0.6f
        }

        else -> {
            1f
        }
    }

    val xOffset = ((5 - awakeCount) * (-8.8)).dp
    Box(
        modifier = Modifier.padding(end = 12.dp)
    ) {
        Box(
            modifier = Modifier.size(width = 47.dp, height = 69.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                GlideImage(
                    modifier = Modifier.padding(start = 1.dp, end = 1.7.dp, top = 1.dp),
                    model = icon,
                    contentDescription = "카드 이미지"
                )
            }
            GlideImage(
                alignment = BiasAlignment(horizontalBias, 0f),
                contentScale = ContentScale.FillHeight,
                model = "https://cdn-lostark.iloa.gg/2018/obt/assets/images/pc/profile/img_card_grade.png",
                contentDescription = "카드 테두리"
            )
        }
        Box(
            modifier = Modifier
                .height(80.6.dp)
                .padding(start = 3.dp, bottom = 3.dp, end = 3.dp)
                .offset(x = (-1).dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Box {
                // 활성화 안된거
                Box(
                    modifier = Modifier.clipToBounds()
                ) {
                    GlideImage(
                        modifier = Modifier.offset(y = 12.6.dp),
                        model = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/profile/img_profile_awake.png",
                        contentDescription = "빈슬롯(위)"
                    )
                }

                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    // 활성화 된거
                    Box(
                        modifier = Modifier.clipToBounds()
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .offset(x = xOffset, y = (-12.6).dp),
                            alignment = BiasAlignment(-1f, 0f),
                            model = "https://cdn-lostark.game.onstove.com/2018/obt/assets/images/pc/profile/img_profile_awake.png",
                            contentDescription = "활성화"
                        )
                    }
                }
            }
        }
    }
}
