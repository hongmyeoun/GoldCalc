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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.searchedInfo.gem.Gem
import com.hongmyeoun.goldcalc.model.searchedInfo.skills.Skills
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.viewModel.charDetail.SkillDetailVM

@Composable
fun SkillDetailUI(
    characterDetail: CharacterDetail?,
    skills: List<Skills>,
    gemList: List<Gem>?,
    viewModel: SkillDetailVM = viewModel()
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
                text = "스킬",
                style = titleTextStyle()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                characterDetail?.let { characterDetail ->
                    TextChip(text = "스킬 포인트 ${characterDetail.usingSkillPoint}/${characterDetail.totalSkillPoint}")
                    Spacer(modifier = Modifier.width(8.dp))
                }
                val (fiveLevel, fourLevel) = viewModel.tripodLevel54(skills)

                if (fiveLevel > 0) {
                    TextChip(text = "Lv.5 x$fiveLevel")
                    Spacer(modifier = Modifier.width(8.dp))
                }
                if (fourLevel > 0) {
                    TextChip(text = "Lv.4 x$fourLevel")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            if (isDetail) {
                SkillDetail(skills, gemList, viewModel)
            } else {
                SkillSimple(skills, gemList, viewModel)
            }
        }
    }
}

@Composable
private fun SkillDetail(
    skills: List<Skills>,
    gemList: List<Gem>?,
    viewModel: SkillDetailVM
) {
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
            SkillDetailRow(it, gemList, viewModel)
        }
    }
}

@Composable
private fun SkillDetailRow(
    it: Skills,
    gemList: List<Gem>?,
    viewModel: SkillDetailVM
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SkillIcon(it)
        SkillTripods(it)
        gemList?.let { gemList ->
            if (it.rune != null || it.gem) {
                SkillRuneAndGem(it, gemList, viewModel)
            }
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
                        contentDescription = "스킬 아이콘"
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
                                borderless = true,
                                customTextSize = 8.sp,
                                customBGColor = BlackTransBG,
                                customPadding = Modifier.padding(start = 2.dp, end = 2.dp)
                            )
                            Spacer(modifier = Modifier.height(1.dp))
                            TextChip(
                                text = tripodLevels,
                                borderless = true,
                                customTextSize = 8.sp,
                                customBGColor = BlackTransBG,
                                customPadding = Modifier.padding(start = 2.dp, end = 2.dp)
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
private fun SkillRuneAndGem(
    it: Skills,
    gemList: List<Gem>,
    viewModel: SkillDetailVM
) {
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
                            brush = viewModel.getItemBG(rune.grade),
                            shape = RoundedCornerShape(30.dp)
                        ),
                    model = rune.icon,
                    contentDescription = "룬"
                )
            }
        }

        if (it.gem) {
            val equipGemList = viewModel.gemFiltering(gemList, it)

            equipGemList.forEach { gem ->
                val gemType = viewModel.typeTrans(gem)

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextChip(text = "${gem.level}${gemType}")
                    GlideImage(
                        modifier = Modifier
                            .background(
                                brush = viewModel.getItemBG(gem.grade),
                                shape = RoundedCornerShape(30.dp)
                            ),
                        model = gem.gemIcon,
                        contentDescription = "보석"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SkillSimple(skills: List<Skills>, gemList: List<Gem>?, viewModel: SkillDetailVM) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                            .padding(4.dp)
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SkillIcon(it, true)
                        Spacer(modifier = Modifier.width(4.dp))
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            SkillSimpleTripods(it)
                            Spacer(modifier = Modifier.height(4.dp))
                            gemList?.let { gemList ->
                                SkillSimpleRuneAndGem(it, gemList, viewModel)
                            }
                        }
                    }
                }
                if (it == skills.last()) {
                    if (skills.size % 2 == 1) {
                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .weight(1f)
                        )
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
            style = titleBoldWhite12()
        )
    }
}

@Composable
private fun SkillSimpleRuneAndGem(
    it: Skills,
    gemList: List<Gem>,
    viewModel: SkillDetailVM
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        it.rune?.let { rune ->
            TextChip(
                text = rune.name,
                customBGColor = viewModel.getGradeBG(rune.grade),
                borderless = true
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        if (it.gem) {
            val equipGemList = gemList.filter { gem -> gem.skill == it.name }
            equipGemList.forEach { gem ->
                val gemType = if (gem.type.contains('멸')) '멸' else '홍'
                val gemInfo = "${gem.level}${gemType}"

                val padding = if (gemInfo.length >= 3 && equipGemList.size > 1) {
                    Modifier.padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
                } else {
                    Modifier.padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 2.dp)
                }
                TextChip(text = gemInfo, borderless = true, customPadding = padding)
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}