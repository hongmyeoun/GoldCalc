package com.hongmyeoun.goldcalc.view.profile.content.skill

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.searchedInfo.skills.Skills
import com.hongmyeoun.goldcalc.ui.theme.BlackTransBG
import com.hongmyeoun.goldcalc.view.common.TextChip

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun SkillIcon(it: Skills, isSimple: Boolean = false) {
    val iconSize = if (isSimple) 44.dp else 54.dp
    Box {
        Column {
            Row {
                Box {
                    GlideImage(
                        modifier = Modifier
                            .size(iconSize)
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
                if (isSimple) {
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            if (isSimple) {
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
        if (isSimple) {
            Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                TextChip(text = "${it.level}")
            }
        }
    }
}
