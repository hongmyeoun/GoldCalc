package com.hongmyeoun.goldcalc.view.goldCheck.setting

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun GoldSettingBottomBar(
    viewModel: GoldSettingVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
    navController: NavHostController
) {
    val height = if (viewModel.expanded) Modifier.wrapContentHeight() else Modifier.height(65.dp)
    val arrowIcon = if (viewModel.expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp

    val character by viewModel.character.collectAsState()

    Box(
        modifier = Modifier
            .animateContentSize()
            .then(height)
            .fillMaxWidth()
            .background(Color.LightGray)
            .clickable { viewModel.expand() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp),
                imageVector = arrowIcon,
                contentDescription = "펼치기, 접기"
            )


            if (viewModel.expanded) {
                Text(text = "${character?.name}(${character?.itemLevel})")
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    if (cbVM.valtanCheck || cbVM.biaCheck || cbVM.koukuCheck || cbVM.abreCheck || cbVM.illiCheck || cbVM.kamenCheck) {
                        Text(text = "군단장 레이드")
                        Text(text = "${cbVM.totalGold}")
                    }
                }

                Row {
                    if (cbVM.valtanCheck) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "발탄")
                            Text(text = "1관 ${cbVM.valtan.onePhase.level} : ${cbVM.valtan.onePhase.totalGold}")
                            Text(text = "2관 ${cbVM.valtan.twoPhase.level} : ${cbVM.valtan.twoPhase.totalGold}")
                        }
                    }
                    if (cbVM.biaCheck) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "비아키스")
                            Text(text = "1관 ${cbVM.biackiss.onePhase.level} : ${cbVM.biackiss.onePhase.totalGold}")
                            Text(text = "2관 ${cbVM.biackiss.twoPhase.level} : ${cbVM.biackiss.twoPhase.totalGold}")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    if (cbVM.koukuCheck) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "쿠크세이튼")
                            Text(text = "1관 ${cbVM.koukuSaton.onePhase.level} : ${cbVM.koukuSaton.onePhase.totalGold}")
                            Text(text = "2관 ${cbVM.koukuSaton.twoPhase.level} : ${cbVM.koukuSaton.twoPhase.totalGold}")
                            Text(text = "3관 ${cbVM.koukuSaton.threePhase.level} : ${cbVM.koukuSaton.threePhase.totalGold}")
                        }
                    }

                    if (cbVM.abreCheck) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "아브렐슈드")
                            Text(text = "1관 ${cbVM.abrelshud.onePhase.level} : ${cbVM.abrelshud.onePhase.totalGold}")
                            Text(text = "2관 ${cbVM.abrelshud.twoPhase.level} : ${cbVM.abrelshud.twoPhase.totalGold}")
                            Text(text = "3관 ${cbVM.abrelshud.threePhase.level} : ${cbVM.abrelshud.threePhase.totalGold}")
                            Text(text = "4관 ${cbVM.abrelshud.fourPhase.level} : ${cbVM.abrelshud.fourPhase.totalGold}")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    if (cbVM.illiCheck) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "일리아칸")
                            Text(text = "1관 ${cbVM.illiakan.onePhase.level} : ${cbVM.illiakan.onePhase.totalGold}")
                            Text(text = "2관 ${cbVM.illiakan.twoPhase.level} : ${cbVM.illiakan.twoPhase.totalGold}")
                            Text(text = "3관 ${cbVM.illiakan.threePhase.level} : ${cbVM.illiakan.threePhase.totalGold}")
                        }
                    }

                    if (cbVM.kamenCheck) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "카멘")
                            Text(text = "1관 ${cbVM.kamen.onePhase.level} : ${cbVM.kamen.onePhase.totalGold}")
                            Text(text = "2관 ${cbVM.kamen.twoPhase.level} : ${cbVM.kamen.twoPhase.totalGold}")
                            Text(text = "3관 ${cbVM.kamen.threePhase.level} : ${cbVM.kamen.threePhase.totalGold}")
                            Text(text = "4관 ${cbVM.kamen.fourPhase.level} : ${cbVM.kamen.fourPhase.totalGold}")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    if (adVM.kayangelCheck || adVM.ivoryCheck) {
                        Text(text = "어비스 던전")
                        Text(text = "${adVM.totalGold}")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    if (adVM.kayangelCheck) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "카양겔")
                            Text(text = "1관 ${adVM.kayangel.onePhase.level} : ${adVM.kayangel.onePhase.totalGold}")
                            Text(text = "2관 ${adVM.kayangel.twoPhase.level} : ${adVM.kayangel.twoPhase.totalGold}")
                            Text(text = "3관 ${adVM.kayangel.threePhase.level} : ${adVM.kayangel.threePhase.totalGold}")
                        }
                    }

                    if (adVM.ivoryCheck) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "혼돈의 상아탑")
                            Text(text = "1관 ${adVM.ivoryTower.onePhase.level} : ${adVM.ivoryTower.onePhase.totalGold}")
                            Text(text = "2관 ${adVM.ivoryTower.twoPhase.level} : ${adVM.ivoryTower.twoPhase.totalGold}")
                            Text(text = "3관 ${adVM.ivoryTower.threePhase.level} : ${adVM.ivoryTower.threePhase.totalGold}")
                            Text(text = "4관 ${adVM.ivoryTower.fourPhase.level} : ${adVM.ivoryTower.fourPhase.totalGold}")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    if (kzVM.echiCheck) {
                        Text(text = "카제로스 레이드")
                        Text(text = "${kzVM.totalGold}")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (kzVM.echiCheck) {
                    Text(text = "에키드나")
                    Text(text = "1관 ${kzVM.echidna.onePhase.level} : ${kzVM.echidna.onePhase.totalGold}")
                    Text(text = "2관 ${kzVM.echidna.twoPhase.level} : ${kzVM.echidna.twoPhase.totalGold}")
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    if (epVM.beheCheck) {
                        Text(text = "에픽 레이드")
                        Text(text = "${epVM.totalGold}")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (epVM.beheCheck) {
                    Text(text = "베히모스")
                    Text(text = "1관 ${epVM.behemoth.onePhase.level} : ${epVM.behemoth.onePhase.totalGold}")
                    Text(text = "2관 ${epVM.behemoth.twoPhase.level} : ${epVM.behemoth.twoPhase.totalGold}")
                }
                Spacer(modifier = Modifier.height(8.dp))

                if ((viewModel.plusGold.toIntOrNull() ?: 0) > 0) {
                    Row {
                        Text(text = "추가골드 : ${viewModel.plusGold}")
                        Text(text = "사용골드 : ${viewModel.minusGold}")
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.weight(3f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "변경 전")
                        Row {
                            GlideImage(
                                modifier = Modifier.size(25.dp),
                                model = R.drawable.gold_coins,
                                contentDescription = "골드 이미지"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "${character?.weeklyGold}")
                        }
                    }
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "화살표")
                    Column {
                        Text(text = "변경 후")
                        Row {
                            GlideImage(
                                modifier = Modifier.size(25.dp),
                                model = R.drawable.gold_coins,
                                contentDescription = "골드 이미지"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "${viewModel.totalGold}")
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.End) {
                    OutlinedButton(onClick = {
                        navController.navigate("Main") {
                            popUpTo("Check/{charName}") {
                                inclusive = true
                            }
                        }
                    }) {
                        Text("취소")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            viewModel.onDoneClick(
                                commandRaid = cbVM,
                                abyssDungeon = adVM,
                                kazerothRaid = kzVM,
                                epicRaid = epVM
                            )
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
}