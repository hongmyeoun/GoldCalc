package com.hongmyeoun.goldcalc.view.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.util.fastSumBy
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.searchedInfo.skills.Skills
import com.hongmyeoun.goldcalc.ui.theme.LegendaryBG

@Composable
fun SkillDetailUI(
    characterDetail: CharacterDetail?,
    skills: List<Skills>
) {
    var isDetail by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isDetail = !isDetail }
    ) {
        Text(text = "스킬")
        characterDetail?.let { characterDetail ->
            TextChip(text = "스킬 포인트 ${characterDetail.usingSkillPoint}/${characterDetail.totalSkillPoint}")
        }
        val fiveLevel = skills.fastSumBy { skill -> skill.tripods.count { tripod -> tripod.isSelected && tripod.level == 5 } }
        val fourLevel = skills.fastSumBy { skill -> skill.tripods.count { tripod -> tripod.isSelected && tripod.level == 4 } }
        if (fiveLevel > 0) {
            TextChip(text = "Lv.5 x $fiveLevel")
        }
        if (fourLevel > 0) {
            TextChip(text = "Lv.4 x $fourLevel")
        }
    }

    if (isDetail) {
        SkillDetail(skills)
    } else {
        SkillSimple(skills)
    }
}

@Composable
private fun SkillDetail(skills: List<Skills>) {
    Column(
        modifier = Modifier.background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "뭐 넣어야 될까",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        skills.forEach {
            SkillDetailRow(it)
        }
    }
}

@Composable
private fun SkillDetailRow(it: Skills) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SkillIcon(it)
        SkillTripods(it)
        if (it.rune != null || it.gem != null) {
            SkillRuneAndGem(it)
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun SkillIcon(it: Skills, isSimple: Boolean = false) {
    Box {
        Column {
            Row {
                Box {
                    GlideImage(
                        modifier = Modifier
                            .size(44.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(RoundedCornerShape(10.dp)),
                        model = it.icon,
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )

                    if (isSimple) {
                        Column(modifier = Modifier.padding(2.dp)) {
                            var tripodSlots = ""
                            var tripodLevels = ""
                            it.tripods.forEach { tripods ->
                                if (tripods.isSelected) {
                                    tripodSlots += tripods.slot.toString()
                                    tripodLevels += tripods.level.toString()
                                }
                            }
                            TextChip(
                                text = tripodSlots,
                                customTextSize = 8.sp,
                                borderless = true,
                                customPadding = Modifier.padding(start = 1.dp, end = 1.dp)
                            )
                            Spacer(modifier = Modifier.height(1.dp))
                            TextChip(
                                text = tripodLevels,
                                customTextSize = 8.sp,
                                borderless = true,
                                customPadding = Modifier.padding(start = 1.dp, end = 1.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            TextChip(text = "${it.level}")
        }
    }
}

@Composable
private fun SkillTripods(it: Skills) {
    Column(horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = it.name,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            it.tripods.let { tripods ->
                tripods.forEach { tripod ->
                    if (tripod.isSelected) {
                        TextChip(text = "${tripod.slot}")
                    }
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            it.tripods.let { tripods ->
                tripods.forEach { tripod ->
                    if (tripod.isSelected) {
                        TextChip(text = "${tripod.level}")
                        Text(
                            text = tripod.name,
                            color = Color.White,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun SkillRuneAndGem(it: Skills) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        it.rune?.let { rune ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextChip(text = rune.name)
                GlideImage(
                    modifier = Modifier
                        .background(
                            brush = LegendaryBG,
                            shape = RoundedCornerShape(30.dp)
                        ),
                    model = rune.icon,
                    contentDescription = "룬"
                )
            }
        }
        it.gem?.let { gems ->
            gems.forEach { gem ->
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextChip(text = gem["name"]!!)
                    GlideImage(
                        modifier = Modifier
                            .background(
                                brush = LegendaryBG,
                                shape = RoundedCornerShape(30.dp)
                            ),
                        model = gem["icon"],
                        contentDescription = "보석"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SkillSimple(skills: List<Skills>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
    ) {
        FlowRow(
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            skills.forEach {
                if (it.level > 3) {
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(
                                color = Color.DarkGray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SkillIcon(it, true)
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            SkillSimpleTripods(it)
                            SkillSimpleRuneAndGem(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SkillSimpleTripods(it: Skills) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = it.name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SkillSimpleRuneAndGem(it: Skills) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        it.rune?.let { rune ->
            TextChip(text = "(${rune.grade})${rune.name}")
        }
        it.gem?.let { gems ->
            gems.forEach { gem ->
                TextChip(text = gem["name"]!!)
            }
        }
    }
}