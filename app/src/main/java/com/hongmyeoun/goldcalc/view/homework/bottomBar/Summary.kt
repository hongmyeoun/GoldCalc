package com.hongmyeoun.goldcalc.view.homework.bottomBar

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.common.formatWithCommas
import com.hongmyeoun.goldcalc.model.constants.Raid
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.homework.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.homework.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.homework.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.homework.HomeworkVM
import com.hongmyeoun.goldcalc.viewModel.homework.KazerothRaidVM

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Summary(
    viewModel: HomeworkVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
    navController: NavHostController
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val maxColumnHeight = (screenHeight * 0.80f)

    val character by viewModel.character.collectAsState()

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = maxColumnHeight),
        onDismissRequest = { viewModel.close() },
        containerColor = LightGrayBG,
        dragHandle = {
            DragDerivationIcon()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .heightIn(max = maxColumnHeight)
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
            ) {

                if (cbVM.valtanCheck || cbVM.biaCheck || cbVM.koukuCheck || cbVM.abreCheck || cbVM.illiCheck || cbVM.kamenCheck) {
                    Script(cbVM)
                }

                if (adVM.kayangelCheck || adVM.ivoryCheck) {
                    Script(adVM)
                }

                if (kzVM.echiCheck) {
                    Script(kzVM)
                }

                if (epVM.beheCheck) {
                    Script(epVM)
                }

                if ((viewModel.plusGold.toIntOrNull() ?: 0) > 0) {
                    Script(viewModel)
                }

                Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                    BottomBarTexts(
                        character = character,
                        viewModel = viewModel,
                        navController = navController,
                        onDoneClicked = {
                            viewModel.close()
                            viewModel.onDoneClick(cbVM, adVM, kzVM, epVM)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun Script(cbVM: CommandBossVM) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        RaidTitle(title = Raid.Name.COMMAND_RAID, totalGold = cbVM.totalGold)
        Divider()

        Row(
            modifier = Modifier.padding(if (cbVM.valtanCheck || cbVM.biaCheck) 16.dp else 0.dp)
        ) {
            PhaseInfo(
                isCheck = cbVM.valtanCheck,
                modifier = Modifier.weight(1f),
                raidName = Raid.Name.VALTAN,
                phaseInfo = {
                    Text(
                        text = "1관문 ${cbVM.valtan.onePhase.level} : ${cbVM.valtan.onePhase.totalGold.formatWithCommas()} G", color = Color.White
                    )
                    Text(text = "2관문 ${cbVM.valtan.twoPhase.level} : ${cbVM.valtan.twoPhase.totalGold.formatWithCommas()} G", color = Color.White)
                }
            )
            PhaseInfo(
                isCheck = cbVM.biaCheck,
                modifier = Modifier.weight(1f),
                raidName = Raid.Name.BIACKISS,
                phaseInfo = {
                    Text(text = "1관문 ${cbVM.valtan.onePhase.level} : ${cbVM.valtan.onePhase.totalGold.formatWithCommas()} G", color = Color.White)
                    Text(text = "2관문 ${cbVM.valtan.twoPhase.level} : ${cbVM.valtan.twoPhase.totalGold.formatWithCommas()} G", color = Color.White)
                }
            )
        }

        Row(
            modifier = Modifier.padding(if (cbVM.koukuCheck || cbVM.abreCheck) 16.dp else 0.dp)
        ) {
            PhaseInfo(
                isCheck = cbVM.koukuCheck,
                modifier = Modifier.weight(1f),
                raidName = Raid.Name.KOUKU_SATON,
                phaseInfo = {
                    Text(
                        text = "1관문 ${cbVM.koukuSaton.onePhase.level} : ${cbVM.koukuSaton.onePhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                    Text(
                        text = "2관문 ${cbVM.koukuSaton.twoPhase.level} : ${cbVM.koukuSaton.twoPhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                    Text(
                        text = "3관문 ${cbVM.koukuSaton.threePhase.level} : ${cbVM.koukuSaton.threePhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                }
            )
            PhaseInfo(
                isCheck = cbVM.abreCheck,
                modifier = Modifier.weight(1f),
                raidName = Raid.Name.ABRELSHUD,
                phaseInfo = {
                    Text(
                        text = "1관문 ${cbVM.abrelshud.onePhase.level} : ${cbVM.abrelshud.onePhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                    Text(
                        text = "2관문 ${cbVM.abrelshud.twoPhase.level} : ${cbVM.abrelshud.twoPhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                    Text(
                        text = "3관문 ${cbVM.abrelshud.threePhase.level} : ${cbVM.abrelshud.threePhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                    Text(
                        text = "4관문 ${cbVM.abrelshud.fourPhase.level} : ${cbVM.abrelshud.fourPhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                }
            )
        }

        Row(
            modifier = Modifier.padding(if (cbVM.illiCheck || cbVM.kamenCheck) 16.dp else 0.dp)
        ) {
            PhaseInfo(
                isCheck = cbVM.illiCheck,
                modifier = Modifier.weight(1f),
                raidName = Raid.Name.ILLIAKAN,
                phaseInfo = {
                    Text(text = "1관문 ${cbVM.illiakan.onePhase.level} : ${cbVM.illiakan.onePhase.totalGold.formatWithCommas()} G", color = Color.White)
                    Text(text = "2관문 ${cbVM.illiakan.twoPhase.level} : ${cbVM.illiakan.twoPhase.totalGold.formatWithCommas()} G", color = Color.White)
                    Text(
                        text = "3관문 ${cbVM.illiakan.threePhase.level} : ${cbVM.illiakan.threePhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                }
            )
            PhaseInfo(
                isCheck = cbVM.kamenCheck,
                modifier = Modifier.weight(1f),
                raidName = Raid.Name.KAMEN,
                phaseInfo = {
                    Text(text = "1관문 ${cbVM.kamen.onePhase.level} : ${cbVM.kamen.onePhase.totalGold.formatWithCommas()} G", color = Color.White)
                    Text(text = "2관문 ${cbVM.kamen.twoPhase.level} : ${cbVM.kamen.twoPhase.totalGold.formatWithCommas()} G", color = Color.White)
                    Text(text = "3관문 ${cbVM.kamen.threePhase.level} : ${cbVM.kamen.threePhase.totalGold.formatWithCommas()} G", color = Color.White)
                    Text(text = "4관문 ${cbVM.kamen.fourPhase.level} : ${cbVM.kamen.fourPhase.totalGold.formatWithCommas()} G", color = Color.White)
                }
            )
        }
    }
}

@Composable
private fun Script(adVM: AbyssDungeonVM) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        RaidTitle(title = Raid.Name.ABYSS_DUNGEON, totalGold = adVM.totalGold)
        Divider()

        Row(
            modifier = Modifier.padding(if (adVM.kayangelCheck || adVM.ivoryCheck) 16.dp else 0.dp)
        ) {
            PhaseInfo(
                isCheck = adVM.kayangelCheck,
                modifier = Modifier.weight(1f),
                raidName = Raid.Name.KAYANGEL,
                phaseInfo = {
                    Text(text = "1관문 ${adVM.kayangel.onePhase.level} : ${adVM.kayangel.onePhase.totalGold.formatWithCommas()} G", color = Color.White)
                    Text(text = "2관문 ${adVM.kayangel.twoPhase.level} : ${adVM.kayangel.twoPhase.totalGold.formatWithCommas()} G", color = Color.White)
                    Text(
                        text = "3관문 ${adVM.kayangel.threePhase.level} : ${adVM.kayangel.threePhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                }
            )
            PhaseInfo(
                isCheck = adVM.ivoryCheck,
                modifier = Modifier.weight(1f),
                raidName = Raid.Name.IVORY_TOWER_LONG,
                phaseInfo = {
                    Text(
                        text = "1관문 ${adVM.ivoryTower.onePhase.level} : ${adVM.ivoryTower.onePhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                    Text(
                        text = "2관문 ${adVM.ivoryTower.twoPhase.level} : ${adVM.ivoryTower.twoPhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                    Text(
                        text = "3관문 ${adVM.ivoryTower.threePhase.level} : ${adVM.ivoryTower.threePhase.totalGold.formatWithCommas()} G",
                        color = Color.White
                    )
                }
            )
        }
    }
}

@Composable
private fun Script(kzVM: KazerothRaidVM) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        RaidTitle(title = Raid.Name.KAZEROTH_RAID, totalGold = kzVM.totalGold)
        Divider()

        Row(
            modifier = Modifier.padding(if (kzVM.echiCheck) 16.dp else 0.dp)
        ) {
            PhaseInfo(
                isCheck = kzVM.echiCheck,
                modifier = Modifier.weight(1f),
                raidName = Raid.Name.ECHIDNA,
                phaseInfo = {
                    Text(text = "1관문 ${kzVM.echidna.onePhase.level} : ${kzVM.echidna.onePhase.totalGold.formatWithCommas()} G", color = Color.White)
                    Text(text = "2관문 ${kzVM.echidna.twoPhase.level} : ${kzVM.echidna.twoPhase.totalGold.formatWithCommas()} G", color = Color.White)
                }
            )
        }
    }
}

@Composable
private fun Script(epVM: EpicRaidVM) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        RaidTitle(title = Raid.Name.EPIC_RAID, totalGold = epVM.totalGold)
        Divider()

        Row(
            modifier = Modifier.padding(if (epVM.beheCheck) 16.dp else 0.dp)
        ) {
            PhaseInfo(
                isCheck = epVM.beheCheck,
                modifier = Modifier.weight(1f),
                raidName = Raid.Name.BEHEMOTH,
                phaseInfo = {
                    Text(text = "1관문 ${epVM.behemoth.onePhase.level} : ${epVM.behemoth.onePhase.totalGold.formatWithCommas()} G", color = Color.White)
                    Text(text = "2관문 ${epVM.behemoth.twoPhase.level} : ${epVM.behemoth.twoPhase.totalGold.formatWithCommas()} G", color = Color.White)
                }
            )
        }
    }
}

@Composable
private fun Script(viewModel: HomeworkVM) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        RaidTitle(title = "기타", totalGold = viewModel.etcGold)
        Divider()

        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    ) {
                        append("추가골드")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append("   ${viewModel.plusGold.formatWithCommas()} G")
                    }
                },
                modifier = Modifier.weight(1f)
            )
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    ) {
                        append("사용골드")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append("   ${viewModel.minusGold.formatWithCommas()} G")
                    }
                },
                modifier = Modifier.weight(1f)
            )

        }
    }
}

@Composable
private fun RaidTitle(title: String, totalGold: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = titleTextStyle(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "${totalGold.formatWithCommas()} G",
            style = titleTextStyle(fontSize = 16.sp)
        )
    }
}

@Composable
private fun PhaseInfo(
    isCheck: Boolean,
    modifier: Modifier,
    raidName: String,
    phaseInfo: @Composable () -> Unit
) {
    if (isCheck) {
        Column(modifier = modifier) {
            Text(
                text = raidName,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))

            phaseInfo()
        }
    }
}