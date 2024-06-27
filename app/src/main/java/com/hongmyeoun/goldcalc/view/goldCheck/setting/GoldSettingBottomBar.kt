package com.hongmyeoun.goldcalc.view.goldCheck.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.SimplephaseInfo
import com.hongmyeoun.goldcalc.ui.theme.GreenQual
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.characterDetail.normalTextStyle
import com.hongmyeoun.goldcalc.view.characterDetail.titleTextStyle
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.view.main.formatWithCommas
import com.hongmyeoun.goldcalc.view.main.goldImage
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM

@Composable
fun GoldSettingBottomBar(
    viewModel: GoldSettingVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
    navController: NavHostController
) {
    BottomBar(
        viewModel = viewModel,
        navController = navController
    ) { viewModel.onDoneClick(cbVM, adVM, kzVM, epVM) }
}

@Composable
fun SimpleSummary(
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
    viewModel: GoldSettingVM
) {
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            if (cbVM.valtanCheck || cbVM.biaCheck || cbVM.koukuCheck || cbVM.abreCheck || cbVM.illiCheck || cbVM.kamenCheck) {
                SimpleCommandRaidInfo(cbVM)
            }

            if (adVM.kayangelCheck || adVM.ivoryCheck) {
                SimpleAbyssDungeonInfo(adVM)
            }

            if (kzVM.echiCheck) {
                SimpleKazerothRaidInfo(kzVM)
            }

            if (epVM.beheCheck) {
                SimpleEpicRaidInfo(epVM)
            }

            if ((viewModel.plusGold.toIntOrNull() ?: 0) > 0) {
                SimpleETCInfo(viewModel)
            }

        }

    }
}

@Composable
private fun SimpleCommandRaidInfo(cbVM: CommandBossVM) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "군단장 레이드",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${cbVM.totalGold.formatWithCommas()} G",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider()

        Row(
            modifier = Modifier.padding(if (cbVM.valtanCheck || cbVM.biaCheck) 16.dp else 0.dp)
        ) {
            SimplephaseInfo(
                isCheck = cbVM.valtanCheck,
                modifier = Modifier.weight(1f),
                raidName = "발탄",
                phaseInfo = {
                    Text(text = "1관문 ${cbVM.valtan.onePhase.level} : ${cbVM.valtan.onePhase.totalGold.formatWithCommas()} G")
                    Text(text = "2관문 ${cbVM.valtan.twoPhase.level} : ${cbVM.valtan.twoPhase.totalGold.formatWithCommas()} G")
                }
            )
            SimplephaseInfo(
                isCheck = cbVM.biaCheck,
                modifier = Modifier.weight(1f),
                raidName = "비아키스",
                phaseInfo = {
                    Text(text = "1관문 ${cbVM.valtan.onePhase.level} : ${cbVM.valtan.onePhase.totalGold.formatWithCommas()} G")
                    Text(text = "2관문 ${cbVM.valtan.twoPhase.level} : ${cbVM.valtan.twoPhase.totalGold.formatWithCommas()} G")
                }
            )
        }

        Row(
            modifier = Modifier.padding(if (cbVM.koukuCheck || cbVM.abreCheck) 16.dp else 0.dp)
        ) {
            SimplephaseInfo(
                isCheck = cbVM.koukuCheck,
                modifier = Modifier.weight(1f),
                raidName = "쿠크세이튼",
                phaseInfo = {
                    Text(text = "1관문 ${cbVM.koukuSaton.onePhase.level} : ${cbVM.koukuSaton.onePhase.totalGold.formatWithCommas()} G")
                    Text(text = "2관문 ${cbVM.koukuSaton.twoPhase.level} : ${cbVM.koukuSaton.twoPhase.totalGold.formatWithCommas()} G")
                    Text(text = "3관문 ${cbVM.koukuSaton.threePhase.level} : ${cbVM.koukuSaton.threePhase.totalGold.formatWithCommas()} G")
                }
            )
            SimplephaseInfo(
                isCheck = cbVM.abreCheck,
                modifier = Modifier.weight(1f),
                raidName = "아브렐슈드",
                phaseInfo = {
                    Text(text = "1관문 ${cbVM.abrelshud.onePhase.level} : ${cbVM.abrelshud.onePhase.totalGold.formatWithCommas()} G")
                    Text(text = "2관문 ${cbVM.abrelshud.twoPhase.level} : ${cbVM.abrelshud.twoPhase.totalGold.formatWithCommas()} G")
                    Text(text = "3관문 ${cbVM.abrelshud.threePhase.level} : ${cbVM.abrelshud.threePhase.totalGold.formatWithCommas()} G")
                    Text(text = "4관문 ${cbVM.abrelshud.fourPhase.level} : ${cbVM.abrelshud.fourPhase.totalGold.formatWithCommas()} G")
                }
            )
        }

        Row(
            modifier = Modifier.padding(if (cbVM.illiCheck || cbVM.kamenCheck) 16.dp else 0.dp)
        ) {
            SimplephaseInfo(
                isCheck = cbVM.illiCheck,
                modifier = Modifier.weight(1f),
                raidName = "일리아칸",
                phaseInfo = {
                    Text(text = "1관문 ${cbVM.illiakan.onePhase.level} : ${cbVM.illiakan.onePhase.totalGold.formatWithCommas()} G")
                    Text(text = "2관문 ${cbVM.illiakan.twoPhase.level} : ${cbVM.illiakan.twoPhase.totalGold.formatWithCommas()} G")
                    Text(text = "3관문 ${cbVM.illiakan.threePhase.level} : ${cbVM.illiakan.threePhase.totalGold.formatWithCommas()} G")
                }
            )
            SimplephaseInfo(
                isCheck = cbVM.kamenCheck,
                modifier = Modifier.weight(1f),
                raidName = "카멘",
                phaseInfo = {
                    Text(text = "1관문 ${cbVM.kamen.onePhase.level} : ${cbVM.kamen.onePhase.totalGold.formatWithCommas()} G")
                    Text(text = "2관문 ${cbVM.kamen.twoPhase.level} : ${cbVM.kamen.twoPhase.totalGold.formatWithCommas()} G")
                    Text(text = "3관문 ${cbVM.kamen.threePhase.level} : ${cbVM.kamen.threePhase.totalGold.formatWithCommas()} G")
                    Text(text = "4관문 ${cbVM.kamen.fourPhase.level} : ${cbVM.kamen.fourPhase.totalGold.formatWithCommas()} G")
                }
            )
        }
    }
}

@Composable
private fun SimpleAbyssDungeonInfo(adVM: AbyssDungeonVM) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "어비스 던전",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${adVM.totalGold.formatWithCommas()} G",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider()

        Row(
            modifier = Modifier.padding(if (adVM.kayangelCheck || adVM.ivoryCheck) 16.dp else 0.dp)
        ) {
            SimplephaseInfo(
                isCheck = adVM.kayangelCheck,
                modifier = Modifier.weight(1f),
                raidName = "카양겔",
                phaseInfo = {
                    Text(text = "1관문 ${adVM.kayangel.onePhase.level} : ${adVM.kayangel.onePhase.totalGold.formatWithCommas()} G")
                    Text(text = "2관문 ${adVM.kayangel.twoPhase.level} : ${adVM.kayangel.twoPhase.totalGold.formatWithCommas()} G")
                    Text(text = "3관문 ${adVM.kayangel.threePhase.level} : ${adVM.kayangel.threePhase.totalGold.formatWithCommas()} G")
                }
            )
            SimplephaseInfo(
                isCheck = adVM.ivoryCheck,
                modifier = Modifier.weight(1f),
                raidName = "혼돈의 상아탑",
                phaseInfo = {
                    Text(text = "1관문 ${adVM.ivoryTower.onePhase.level} : ${adVM.ivoryTower.onePhase.totalGold.formatWithCommas()} G")
                    Text(text = "2관문 ${adVM.ivoryTower.twoPhase.level} : ${adVM.ivoryTower.twoPhase.totalGold.formatWithCommas()} G")
                    Text(text = "3관문 ${adVM.ivoryTower.threePhase.level} : ${adVM.ivoryTower.threePhase.totalGold.formatWithCommas()} G")
                    Text(text = "4관문 ${adVM.ivoryTower.fourPhase.level} : ${adVM.ivoryTower.fourPhase.totalGold.formatWithCommas()} G")
                }
            )
        }

    }
}

@Composable
private fun SimpleKazerothRaidInfo(kzVM: KazerothRaidVM) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "카제로스 레이드",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${kzVM.totalGold.formatWithCommas()} G",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider()

        Row(
            modifier = Modifier.padding(if (kzVM.echiCheck) 16.dp else 0.dp)
        ) {
            SimplephaseInfo(
                isCheck = kzVM.echiCheck,
                modifier = Modifier.weight(1f),
                raidName = "에키드나",
                phaseInfo = {
                    Text(text = "1관문 ${kzVM.echidna.onePhase.level} : ${kzVM.echidna.onePhase.totalGold.formatWithCommas()} G")
                    Text(text = "2관문 ${kzVM.echidna.twoPhase.level} : ${kzVM.echidna.twoPhase.totalGold.formatWithCommas()} G")
                }
            )
        }
    }
}

@Composable
private fun SimpleEpicRaidInfo(epVM: EpicRaidVM) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "에픽 레이드",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${epVM.totalGold.formatWithCommas()} G",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider()

        Row(
            modifier = Modifier.padding(if (epVM.beheCheck) 16.dp else 0.dp)
        ) {
            SimplephaseInfo(
                isCheck = epVM.beheCheck,
                modifier = Modifier.weight(1f),
                raidName = "베히모스",
                phaseInfo = {
                    Text(text = "1관문 ${epVM.behemoth.onePhase.level} : ${epVM.behemoth.onePhase.totalGold.formatWithCommas()} G")
                    Text(text = "2관문 ${epVM.behemoth.twoPhase.level} : ${epVM.behemoth.twoPhase.totalGold.formatWithCommas()} G")
                }
            )
        }
    }
}

@Composable
private fun SimpleETCInfo(viewModel: GoldSettingVM) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "기타",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${viewModel.etcGold.formatWithCommas()} G",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider()

        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("추가골드")
                    }

                    append("   ${viewModel.plusGold.formatWithCommas()} G")
                },
                modifier = Modifier.weight(1f)
            )
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("사용골드")
                    }

                    append("   ${viewModel.minusGold.formatWithCommas()} G")
                },
                modifier = Modifier.weight(1f)
            )

        }
    }
}

@Composable
private fun BottomBar(
    viewModel: GoldSettingVM,
    navController: NavHostController,
    onDoneClicked: () -> Unit
) {
    val character by viewModel.character.collectAsState()

    val borderPadding = if (viewModel.expanded) Modifier
        .border(1.dp, Color.LightGray)
        .padding(start = 16.dp, end = 8.dp, bottom = 16.dp, top = 16.dp) else Modifier.padding(start = 16.dp, end = 8.dp, bottom = 16.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LightGrayBG,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .noRippleClickable { viewModel.expand() }
            .then(borderPadding)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(4.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(3f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomGoldText(beforeOrAfter = "전", gold = character?.weeklyGold ?: 0)
                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    tint = Color.White,
                    contentDescription = "화살표"
                )
                Spacer(modifier = Modifier.width(12.dp))

                BottomGoldText(beforeOrAfter = "후", gold = viewModel.totalGold)
            }
            Row(horizontalArrangement = Arrangement.End) {
                OutlinedButton(
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    onClick = {
                        navController.navigate("Main") {
                            popUpTo("Check/{charName}") {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text("취소")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenQual,
                        contentColor = Color.White
                    ),
                    onClick = {
                        onDoneClicked()
                        navController.navigate("Main") {
                            popUpTo("Check/{charName}") {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text(text = "완료")
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BottomGoldText(beforeOrAfter: String, gold: Int) {
    Column {
        Text(
            text = "변경 $beforeOrAfter",
            style = titleTextStyle(fontSize = 18.sp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier.size(18.dp),
                model = goldImage(gold),
                contentDescription = "골드 이미지",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = gold.formatWithCommas(),
                style = normalTextStyle(fontSize = 14.sp)
            )
        }
    }
}