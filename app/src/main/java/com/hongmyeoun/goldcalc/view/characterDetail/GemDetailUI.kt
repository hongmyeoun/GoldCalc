package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.searchedInfo.gem.Gem
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.ui.theme.RelicBG
import com.hongmyeoun.goldcalc.ui.theme.RelicColor
import kotlin.math.absoluteValue

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun GemDetailUI(gemList: List<Gem>) {
    val annihilation = gemList.count { it.type == "멸화" }
    val crimsonFlame = gemList.size - annihilation

    var isDetail by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().clickable { isDetail = !isDetail }) {
            Text(
                text = "보석",
                style = titleTextStyle()
            )
            Spacer(modifier = Modifier.height(4.dp))

        }
        if (isDetail) {
            Row {
                GemDetail(
                    modifier = Modifier.weight(1f),
                    effectType = "피해",
                    gemList = gemList
                )
                GemDetail(
                    modifier = Modifier.weight(1f),
                    effectType = "재사용 대기시간",
                    gemList = gemList
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextChip(text = "멸화 x$annihilation")
                Spacer(modifier = Modifier.width(8.dp))
                TextChip(text = "홍염 x$crimsonFlame")
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row {
                val (annMaxItemCount, criMaxItemCount) = calcMaxItemsInEachRow(annihilation, crimsonFlame)
                Column {
                    FlowRow(
                        maxItemsInEachRow = annMaxItemCount
                    ) {
                        gemList.filter { it.type == "멸화" }.forEach {
                            GemSimple(it)
                        }
                    }
                }
                Column {
                    FlowRow(
                        maxItemsInEachRow = criMaxItemCount
                    ) {
                        gemList.filter { it.type == "홍염" }.forEach {
                            GemSimple(it)
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

private fun calcMaxItemsInEachRow(ann: Int, cri: Int): Pair<Int, Int> {
    val difference = (ann - cri).absoluteValue
    return when {
        difference < 7 -> if (ann > cri) Pair(4, 3) else Pair(3, 4)
        difference < 9 -> if (ann > cri) Pair(5, 2) else Pair(2, 5)
        difference < 11 -> if (ann > cri) Pair(6, 1) else Pair(1, 6)
        else -> if (ann > cri) Pair(7, 0) else Pair(0, 7)
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GemDetail(
    modifier: Modifier,
    effectType: String,
    gemList: List<Gem>
) {
    Column(
        modifier = modifier.background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(
                    color = LightGrayBG,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = effectType,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        val type = if (effectType == "피해") "멸화" else "홍염"

        gemList.filter { it.type == type }.forEach { gem ->
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                Box {
                    Column {
                        Row {
                            GlideImage(
                                modifier = Modifier
                                    .size(44.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clip(RoundedCornerShape(10.dp)),
                                model = gem.skillIcon,
                                contentScale = ContentScale.Crop,
                                contentDescription = "보석 아이콘"
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                        TextChip(text = "${gem.level}")
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = gem.skill,
                        color = RelicColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = gem.effect.substringAfter("$effectType "),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun GemSimple(it: Gem) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = LightGrayBG,
                shape = RoundedCornerShape(4.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier
                .size(44.dp)
                .background(
                    brush = RelicBG,
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                )
                .padding(4.dp),
            model = it.gemIcon,
            contentDescription = "보석",
        )
        Text(text = "${it.level}", fontSize = 10.sp, color = Color.White)
    }
}
