package com.hongmyeoun.goldcalc.view.home.topbar.progressText

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.common.ImageReturn
import com.hongmyeoun.goldcalc.model.common.formatWithCommas
import com.hongmyeoun.goldcalc.model.common.toPercentage
import com.hongmyeoun.goldcalc.model.constants.viewConst.Home
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.ui.theme.CharacterEmblemBG
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.home.HomeVM
import kotlin.math.absoluteValue

@Composable
fun SimpleCurrent(viewModel: HomeVM) {
    Dialog(onDismissRequest = { viewModel.onDismissRequest() }) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val maxColumnHeight = (screenHeight * 0.8f) // 화면 높이의 80%

        Column(
            modifier = Modifier
                .background(LightGrayBG, RoundedCornerShape(16.dp))
                .heightIn(max = maxColumnHeight)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentTop()
            CurrentContents(viewModel)
        }
    }
}

@Composable
private fun CurrentTop() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = Home.CURRENT_HW,
        textAlign = TextAlign.Center,
        style = titleTextStyle(fontSize = 20.sp)
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun CurrentContents(viewModel: HomeVM) {
    val characterList by viewModel.characters.collectAsState()
    LazyColumn {
        items(characterList, key = { item -> item.name }) {
            Content(it)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Content(character: Character) {
    Column(
        modifier = Modifier
            .background(
                color = ImageBG,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(2f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(36.dp))
                        .background(CharacterEmblemBG),
                    contentAlignment = Alignment.Center
                ) {
                    GlideImage(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(3.dp),
                        contentScale = ContentScale.Crop,
                        model = CharacterResourceMapper.getClassEmblem(character.className),
                        contentDescription = "직업군"
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = character.serverName,
                        style = normalTextStyle()
                    )

                    Text(
                        text = character.name,
                        style = normalTextStyle(fontSize = 12.sp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = character.className,
                            style = normalTextStyle()
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = character.itemLevel,
                            style = normalTextStyle()
                        )
                    }
                }


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideImage(
                            modifier = Modifier.size(24.dp),
                            model = ImageReturn.goldImage(character.weeklyGold),
                            contentDescription = "골드"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            text = character.weeklyGold.formatWithCommas(),
                            style = normalTextStyle(fontSize = 14.sp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideImage(
                            modifier = Modifier.size(24.dp),
                            model = ImageReturn.goldImage(character.earnGold),
                            contentDescription = "골드"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            text = character.earnGold.formatWithCommas(),
                            style = normalTextStyle(fontSize = 12.sp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        val extraGold = character.plusGold.toInt() - character.minusGold.toInt()
        val totalGold = character.earnGold + extraGold
        val maxGold = character.weeklyGold
        val remainGold = maxGold - totalGold
        val totalPercentage = maxGold + extraGold.absoluteValue

        val percentage = if (character.weeklyGold != 0) 1 - (remainGold.toFloat() / totalPercentage) else 0.0f


        Box(contentAlignment = Alignment.Center) {
            LinearProgressIndicator(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
                    .border(
                        width = 0.5f.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    ),
                progress = percentage,
                color = CharacterEmblemBG,
                trackColor = Color.Transparent,
                strokeCap = StrokeCap.Round
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                text = percentage.toPercentage(),
                textAlign = TextAlign.End,
                style = normalTextStyle(fontSize = 12.sp)
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}
