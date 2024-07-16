package com.hongmyeoun.goldcalc.view.profile.content.engraving

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.common.htmlStyledText
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig.Companion.ARK_PASSIVE_ACTIVATION_ICON
import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts.GRADE_EPIC
import com.hongmyeoun.goldcalc.model.constants.viewConst.Profile
import com.hongmyeoun.goldcalc.model.profile.engravings.SkillEngravings
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.ui.theme.LightGrayTransBG
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.profile.EngravingVM

@Composable
fun EngravingView(
    modifier: Modifier,
    skillEngravings: List<SkillEngravings>,
    isArkPassive: Boolean,
    viewModel: EngravingVM = viewModel()
) {
    val showDialog by viewModel.showDialog.collectAsState()

    if (showDialog) {
        Description(
            skillEngravings = skillEngravings,
            viewModel = viewModel,
            isArkPassive = isArkPassive
        )
    }

    Column(
        modifier = modifier
            .background(LightGrayTransBG, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isArkPassive) {
            ArkPassive(
                skillEngravings = skillEngravings,
                viewModel = viewModel
            )
        } else {
            NoArkPassive(
                skillEngravings = skillEngravings,
                viewModel = viewModel
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ArkPassive(
    skillEngravings: List<SkillEngravings>,
    viewModel: EngravingVM
) {
    skillEngravings.sortedByDescending { it.abilityStoneLevel }.forEach {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { viewModel.onClicked(it.name) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = it.name,
                style = titleTextStyle(fontSize = 15.sp, color = viewModel.textColor(it.grade ?: GRADE_EPIC))
            )
            Spacer(modifier = Modifier.width(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (it.abilityStoneLevel != null) {
                    GlideImage(
                        modifier = Modifier.size(16.dp),
                        model = viewModel.stoneImg(it.name),
                        contentDescription = "어빌리티 활성도"
                    )
                    Text(
                        text = "Lv.${it.abilityStoneLevel}",
                        style = titleTextStyle(fontSize = 14.sp, color = viewModel.textColor(it.grade ?: GRADE_EPIC))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }

                if (it.grade != null) {
                    GlideImage(
                        modifier = Modifier.size(16.dp),
                        model = viewModel.engGrade(it.grade),
                        contentDescription = "각인서 활성도"
                    )
                    Text(
                        text = "x${it.level}",
                        style = titleTextStyle(fontSize = 14.sp, color = viewModel.textColor(it.grade))
                    )
                }
            }
        }

        if (it != skillEngravings.last()) {
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun NoArkPassive(
    skillEngravings: List<SkillEngravings>,
    viewModel: EngravingVM
) {
    skillEngravings.forEach {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { viewModel.onClicked(it.name) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = it.icon,
                contentDescription = "각인 아이콘"
            )
            Spacer(modifier = Modifier.width(12.dp))

            Text(text = it.level, style = titleTextStyle())
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                val textSize = if (it.name.length > 8) 10.sp else if (it.name.length > 7) 13.sp else 15.sp
                Text(
                    text = it.name,
                    style = titleTextStyle(fontSize = textSize)
                )
                if (it.awakenEngravingsPoint != null) {
                    Text(
                        text = "각인서 ${it.awakenEngravingsPoint}",
                        style = normalTextStyle(fontSize = 12.sp)
                    )
                }
            }
        }

        if (it != skillEngravings.last()) {
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun Description(
    skillEngravings: List<SkillEngravings>,
    viewModel: EngravingVM,
    isArkPassive: Boolean,
) {
    val engraving = viewModel.getEngraving(skillEngravings)

    engraving?.let {
        Dialog(
            onDismissRequest = { viewModel.onDismissRequest() }
        ) {
            Column(
                modifier = Modifier
                    .background(ImageBG, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${Profile.ENGRAVING} ${Profile.Detail}",
                    style = titleTextStyle()
                )

                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                if (isArkPassive) {
                    ArkPassiveDetail(it, viewModel)

                } else {
                    NoArkPassiveDetail(it)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ArkPassiveDetail(
    it: SkillEngravings,
    viewModel: EngravingVM
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = ARK_PASSIVE_ACTIVATION_ICON,
            contentDescription = "각인 아이콘"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = it.name,
            style = titleTextStyle(fontSize = 15.sp, color = viewModel.textColor(it.grade ?: GRADE_EPIC))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            if (it.abilityStoneLevel != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        modifier = Modifier.size(16.dp),
                        model = viewModel.stoneImg(it.name),
                        contentDescription = "어빌리티 활성도"
                    )
                    Text(
                        text = "Lv.${it.abilityStoneLevel}",
                        style = titleTextStyle(fontSize = 14.sp, color = viewModel.textColor(it.grade ?: GRADE_EPIC))
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            if (it.grade != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        modifier = Modifier.size(16.dp),
                        model = viewModel.engGrade(it.grade),
                        contentDescription = "각인서 활성도"
                    )
                    Text(
                        text = "x${it.level}",
                        style = titleTextStyle(fontSize = 14.sp, color = viewModel.textColor(it.grade))
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    Box(
        modifier = Modifier
            .background(LightGrayBG, RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = htmlStyledText(it.description),
            style = normalTextStyle(fontSize = 12.sp),
            lineHeight = 16.sp,
            softWrap = true
        )
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun NoArkPassiveDetail(it: SkillEngravings) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = it.icon,
            contentDescription = "각인 아이콘"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Lv.${it.level} ${it.name}",
            style = titleTextStyle(fontSize = 15.sp)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

    Box(
        modifier = Modifier
            .background(LightGrayBG, RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = htmlStyledText(it.description),
            style = normalTextStyle(fontSize = 12.sp),
            lineHeight = 16.sp,
            softWrap = true
        )
    }
}
