package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.searchedInfo.engravings.SkillEngravings
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EngravingDetailUI(skillEngravings: List<SkillEngravings>) {
    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {

        skillEngravings.forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = it.icon,
                    contentDescription = "각인 아이콘"
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(text = it.level, style = titleTextStyle())
                Spacer(modifier = Modifier.width(4.dp))

                Column {
                    Text(text = it.name, style = titleTextStyle(fontSize = 15.sp))
                    if (it.awakenEngravingsPoint != null) {
                        Text(text = "각인서 ${it.awakenEngravingsPoint}", style = normalTextStyle(fontSize = 12.sp))
                    }
                }
            }

            if (it != skillEngravings.last()) {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

    }
    Spacer(modifier = Modifier.height(16.dp))
}