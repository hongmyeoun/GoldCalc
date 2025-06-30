package com.hongmyeoun.goldcalc.view.common.profileTemplate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig
import com.hongmyeoun.goldcalc.model.constants.viewConst.Homework
import com.hongmyeoun.goldcalc.model.lostArkApi.SearchedCharacterDetail
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassive
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveEnlightenment
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveEvolution
import com.hongmyeoun.goldcalc.ui.theme.ArkPassiveLeap
import com.hongmyeoun.goldcalc.ui.theme.VeryLightGrayTransBG
import com.hongmyeoun.goldcalc.view.common.TextChip
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.view.profile.titleBoldWhite12
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle

// 서버이름, 직업
@Composable
fun ServerClassName(serverName: String, className: String) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        TextChip(
            text = serverName,
            borderless = true,
            customTextSize = 12.sp,
            customBGColor = VeryLightGrayTransBG
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = className,
            style = normalTextStyle()
        )
    }
}

// 칭호, 닉네임
@Composable
fun TitleCharName(title: String?, name: String) {
    Spacer(modifier = Modifier.height(12.dp))

    title?.let {
        Text(
            text = title,
            style = normalTextStyle()
        )
    }

    Text(
        text = name,
        style = titleTextStyle()
    )
    Spacer(modifier = Modifier.height(12.dp))
}

// 아이템 레벨
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemLevelOrCombatPower(level: String, noSpace: Boolean = false, isCombatPower: Boolean = false) {
    val iconModel = if (isCombatPower) NetworkConfig.COMBAT_POWER_ICON else NetworkConfig.ITEM_LEVEL_ICON
    val textColor = if (isCombatPower) Color(0xFFF7B838) else Color(0xFFFF009B)

    Row(verticalAlignment = Alignment.CenterVertically) {
        GlideImage(
            modifier = Modifier.size(24.dp),
            model = iconModel,
            contentScale = ContentScale.FillHeight,
            contentDescription = "아이템 레벨"
        )
        val dot = Homework.DOT
        val beforeDot = level.substringBefore(dot)
        val afterDot = level.substringAfter(dot)
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor
                    )
                ) {
                    append(beforeDot)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor
                    )
                ) {
                    append(dot)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor
                    )
                ) {
                    append(afterDot)
                }
            }
        )
    }
    if (!noSpace) {
        Spacer(modifier = Modifier.height(12.dp))
    }
}

// 길드, 영지, PVP 등급(homework)
@Composable
fun Extra(character: Character) {
    ExtraInfo(Homework.GUILD, character.guildName)
    Spacer(modifier = Modifier.height(6.dp))

    ExtraInfo(Homework.TOWN, if (character.townLevel != null) "Lv.${character.townLevel} ${character.townName}" else Homework.NULL_VALUE)
    Spacer(modifier = Modifier.height(6.dp))

    ExtraInfo(Homework.PVP, character.pvpGradeName.ifEmpty { Homework.NULL_VALUE })
    Spacer(modifier = Modifier.height(8.dp))
}

// 길드, 영지, PVP 등급(Profile)
@Composable
fun Extra(profile: SearchedCharacterDetail) {
    ExtraInfo(Homework.GUILD, profile.guildName)
    Spacer(modifier = Modifier.height(6.dp))

    ExtraInfo(Homework.TOWN, "Lv.${profile.townLevel} ${profile.townName}")
    Spacer(modifier = Modifier.height(6.dp))

    ExtraInfo(Homework.PVP, profile.pvpGradeName)
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun ExtraInfo(extraTitle: String, extraDetail: String?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        TextChip(text = extraTitle, borderless = true, customBGColor = VeryLightGrayTransBG)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = extraDetail ?: Homework.NULL_VALUE,
            style = normalTextStyle()
        )
    }
}

// 전투, 원정대 레벨(Homework)
@Composable
fun Levels(character: Character) {
    Row {
        CharLevel(Homework.CHARACTER_LEVEL, character.characterLevel.toString())
        Spacer(modifier = Modifier.width(16.dp))

        CharLevel(Homework.EXPEDITION_LEVEL, character.expeditionLevel.toString())
    }
}

// 전투, 원정대 레벨(Profile)
@Composable
fun Levels(profile: SearchedCharacterDetail) {
    Row {
        CharLevel(Homework.CHARACTER_LEVEL, profile.characterLevel.toString())
        Spacer(modifier = Modifier.width(16.dp))

        CharLevel(Homework.EXPEDITION_LEVEL, profile.expeditionLevel.toString())
    }
}

// 아이템 레벨
@Composable
private fun CharLevel(title: String, level: String) {
    Column {
        Text(
            text = title,
            style = titleBoldWhite12()
        )
        if (title == Homework.ITEM_LEVEL) {
            val dot = Homework.DOT
            val beforeDot = level.substringBefore(dot)
            val afterDot = level.substringAfter(dot)
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    ) {
                        append(beforeDot)
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    ) {
                        append(dot)
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    ) {
                        append(afterDot)
                    }
                }
            )
        } else {
            Text(text = "Lv.$level", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArkPassive(
    character: Character,
) {
    Row {
        Box(
            modifier = Modifier.size(width = 58.dp, height = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = R.drawable.bg_arkpassive_evolution,
                contentScale = ContentScale.FillBounds,
                contentDescription = "진화"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "   ${character.evolutionLevel}",
                color = ArkPassiveEvolution,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier.size(width = 58.dp, height = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = R.drawable.bg_arkpassive_enlightenment,
                contentScale = ContentScale.FillBounds,
                contentDescription = "깨달음"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "   ${character.enlightenmentLevel}",
                color = ArkPassiveEnlightenment,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier.size(width = 58.dp, height = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = R.drawable.bg_arkpassive_leap,
                contentScale = ContentScale.FillBounds,
                contentDescription = "도약"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "   ${character.leapLevel}",
                color = ArkPassiveLeap,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArkPassive(
    arkPassive: ArkPassive,
) {
    Row {
        Box(
            modifier = Modifier.size(width = 58.dp, height = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = R.drawable.bg_arkpassive_evolution,
                contentScale = ContentScale.FillBounds,
                contentDescription = "진화"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "   ${arkPassive.points[0].value}",
                color = ArkPassiveEvolution,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier.size(width = 58.dp, height = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = R.drawable.bg_arkpassive_enlightenment,
                contentScale = ContentScale.FillBounds,
                contentDescription = "깨달음"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "   ${arkPassive.points[1].value}",
                color = ArkPassiveEnlightenment,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier.size(width = 58.dp, height = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = R.drawable.bg_arkpassive_leap,
                contentScale = ContentScale.FillBounds,
                contentDescription = "도약"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "   ${arkPassive.points[2].value}",
                color = ArkPassiveLeap,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}