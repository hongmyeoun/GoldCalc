package com.hongmyeoun.goldcalc.view.profile.content.skill

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hongmyeoun.goldcalc.model.constants.Profile
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.profile.gem.Gem
import com.hongmyeoun.goldcalc.model.profile.skills.Skills
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.view.profile.Title
import com.hongmyeoun.goldcalc.viewModel.profile.ProfileVM
import com.hongmyeoun.goldcalc.viewModel.profile.SkillVM

@Composable
fun Skill(
    viewModel: ProfileVM,
    profile: CharacterDetail
) {
    // 스킬
    val skills by viewModel.skills.collectAsState()

    // 보석
    val gems by viewModel.gems.collectAsState()

    skills?.let { skillsList ->
        SkillView(profile, skillsList, gems)
    }
}

@Composable
fun SkillView(
    characterDetail: CharacterDetail?,
    skills: List<Skills>,
    gemList: List<Gem>?,
    viewModel: SkillVM = viewModel()
) {
    val isDetail by viewModel.isDetail.collectAsState()

    Column(
        modifier = Modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .noRippleClickable { viewModel.onDetailClicked() }
            .padding(8.dp)
    ) {
        Title(
            title = Profile.SKILL,
            onClick = { viewModel.onDetailClicked() }
        )

        SkillPoint(
            characterDetail = characterDetail,
            skills = skills,
            viewModel = viewModel
        )

        if (isDetail) {
            Detail(skills, gemList, viewModel)
        } else {
            Simple(skills, gemList, viewModel)
        }
    }
}

@Composable
fun SkillPoint(
    characterDetail: CharacterDetail?,
    skills: List<Skills>,
    viewModel: SkillVM,
) {
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
}
