package com.hongmyeoun.goldcalc.view.home.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.common.ImageReturn
import com.hongmyeoun.goldcalc.model.common.formatWithCommas
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.viewModel.main.CharacterListVM

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun CurrentGold(viewModel: CharacterListVM) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "얻은 골드",
                style = normalTextStyle(),
                fontWeight = FontWeight.Light
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier.size(25.dp),
                    model = ImageReturn.goldImage(viewModel.earnGold),
                    contentDescription = "골드"
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = viewModel.earnGold.formatWithCommas(),
                    style = normalTextStyle(fontSize = 16.sp),
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "남은 골드",
                style = normalTextStyle(),
                fontWeight = FontWeight.Light
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier.size(25.dp),
                    model = ImageReturn.goldImage(viewModel.remainGold),
                    contentDescription = "골드"
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = viewModel.remainGold.formatWithCommas(),
                    style = normalTextStyle(fontSize = 16.sp),
                )
            }
        }
    }
}