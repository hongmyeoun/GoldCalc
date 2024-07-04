package com.hongmyeoun.goldcalc.view.profile.content.gem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.RelicColor
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.common.TextChip

@Composable
fun Detail(
    gemList: List<Gem>,
) {
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
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun GemDetail(
    modifier: Modifier,
    effectType: String,
    gemList: List<Gem>
) {
    Column(
        modifier = modifier.background(BlackTransBG, RoundedCornerShape(4.dp))
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
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = gem.effect.substringAfter("$effectType "),
                        style = normalTextStyle(fontSize = 12.sp)
                    )
                }
            }
        }
    }
}
