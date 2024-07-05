package com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.common.ImageReturn
import com.hongmyeoun.goldcalc.model.common.formatWithCommas

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TitleCard(
    raidImg: Int,
    totalGold: Int,
    raidContent: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        GlideImage(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            model = raidImg,
            contentDescription = "던전 아이콘"
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            GlideImage(
                modifier = Modifier.size(25.dp),
                model = ImageReturn.goldImage(totalGold),
                contentDescription = "골드 아이콘"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = totalGold.formatWithCommas(),
                color = Color.White
            )
        }

    }

    raidContent()
}
