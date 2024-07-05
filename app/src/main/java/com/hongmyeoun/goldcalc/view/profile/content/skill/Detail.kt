package com.hongmyeoun.goldcalc.view.profile.content.skill

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.hongmyeoun.goldcalc.model.profile.gem.Gem
import com.hongmyeoun.goldcalc.model.profile.skills.Skills
import com.hongmyeoun.goldcalc.model.profile.skills.Tripods
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.ui.theme.PurpleQual
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.viewModel.profile.SkillVM

@Composable
fun Detail(
    skills: List<Skills>,
    gemList: List<Gem>?,
    viewModel: SkillVM
) {
    Column(
        modifier = Modifier
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "스킬 상세",
                style = titleTextStyle()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        skills.forEach {
            DetailRow(it, gemList, viewModel)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun DetailRow(
    it: Skills,
    gemList: List<Gem>?,
    viewModel: SkillVM
) {
    Column(
        modifier = Modifier
            .background(
                color = LightGrayBG,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkillIcon(it)
            Spacer(modifier = Modifier.width(4.dp))

            Tripods(it, viewModel)
        }
        Spacer(modifier = Modifier.height(8.dp))

        gemList?.let { gemList ->
            if (it.rune != null || it.gem) {
                RuneAndGem(it, gemList, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Tripods(it: Skills, viewModel: SkillVM) {
    Column(horizontalAlignment = Alignment.Start) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextChip(text = "${it.level}", customTextSize = 12.sp, customBGColor = PurpleQual)
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = it.name,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))

            it.tripods.let { tripods ->
                var selectedIndex = 0

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    tripods.forEach { tripod ->
                        if (tripod.isSelected) {
                            TextChip(
                                text = "${tripod.slot}",
                                borderless = true,
                                customTextSize = 14.sp,
                                customRoundedCornerSize = 30.dp,
                                customBGColor = viewModel.getIndexColor(selectedIndex)
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            selectedIndex++
                        }
                    }

                }

            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 3
        ) {
            it.tripods.let { tripods ->
                var selectedIndex = 0
                tripods.forEach { tripod ->
                    if (tripod.isSelected) {
                        TripodInfo(tripod, viewModel, selectedIndex)
                        selectedIndex++
                    }
                }
            }
        }
    }
}

@Composable
private fun TripodInfo(
    tripod: Tripods,
    viewModel: SkillVM,
    selectedIndex: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextChip(
            text = "${tripod.level}",
            borderless = true,
            customBGColor = viewModel.getIndexColor(selectedIndex)
        )
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = tripod.name,
            style = normalTextStyle(color = viewModel.getIndexColor(selectedIndex), fontSize = 12.sp)
        )
        Spacer(modifier = Modifier.width(4.dp))
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun RuneAndGem(
    it: Skills,
    gemList: List<Gem>,
    viewModel: SkillVM
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        it.rune?.let { rune ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    modifier = Modifier
                        .size(54.dp)
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(
                            brush = viewModel.getItemBG(rune.grade),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp)),
                    model = rune.icon,
                    contentScale = ContentScale.Crop,
                    contentDescription = "룬"
                )

                Column {
                    Row {
                        Spacer(modifier = Modifier.width(8.dp))
                        TextChip(text = rune.grade, customTextSize = 12.sp, borderless = true, customBGColor = viewModel.getGradeBG(rune.grade))
                        TextChip(text = rune.name, customTextSize = 12.sp, borderless = true, customBGColor = LightGrayTransBG)
                    }
                    it.runeTooltip?.let { tooltip ->
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = tooltip,
                                style = normalTextStyle()
                            )
                        }
                    }
                }
            }
        }

        if (it.gem) {
            val equipGemList = viewModel.gemFiltering(gemList, it)

            equipGemList.forEach { gem ->
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        modifier = Modifier
                            .size(54.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .background(
                                brush = viewModel.getItemBG(gem.grade),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(RoundedCornerShape(10.dp)),
                        model = gem.gemIcon,
                        contentScale = ContentScale.Crop,
                        contentDescription = "보석"
                    )
                    Column {
                        Row {
                            Spacer(modifier = Modifier.width(8.dp))
                            TextChip(
                                text = "${gem.level}레벨 ${gem.type}의 보석",
                                customTextSize = 12.sp,
                                borderless = true,
                                customBGColor = viewModel.getGradeBG(gem.grade)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 24.dp)
                                .background(LightGrayBG, RoundedCornerShape(4.dp))
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = gem.effect,
                                style = normalTextStyle()
                            )
                        }

                    }

                }
            }
        }
    }
}
