package com.hongmyeoun.goldcalc.view.home.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.Raid
import com.hongmyeoun.goldcalc.viewModel.home.GoldContentStateVM
import com.hongmyeoun.goldcalc.viewModel.home.HomeContentVM

@Composable
fun HomeworkProgress(
    viewModel: HomeContentVM,
) {
    val character by viewModel.character.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 열당 2개의 아이템을 보여주도록 설정
        modifier = Modifier
            .fillMaxSize()
            .heightIn(max = 1000.dp)
            .wrapContentHeight(),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (character.checkList.epic[0].phases[0].isClear) {
            item {
                val behemothVM = remember { GoldContentStateVM(character.raidPhaseInfo.behemothPhase) }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.epic[0].phases),
                    raidImg = R.drawable.epic_behemoth,
                    raidName = Raid.Name.BEHEMOTH,
                    viewModel = behemothVM,
                    onClicked = { viewModel.beheGoldCalc(it) }
                )
            }
        }
        if (character.checkList.kazeroth[0].phases[0].isClear) {
            item {
                val echidnaVM = remember { GoldContentStateVM(character.raidPhaseInfo.echidnaPhase) }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.kazeroth[0].phases),
                    raidImg = R.drawable.kazeroth_echidna,
                    raidName = Raid.Name.ECHIDNA,
                    viewModel = echidnaVM,
                    onClicked = { viewModel.echiGoldCalc(it) }
                )
            }
        }
        if (character.checkList.command[5].phases[0].isClear) {
            item {
                val kamenVM = remember { GoldContentStateVM(character.raidPhaseInfo.kamenPhase) }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.command[5].phases),
                    raidImg = R.drawable.command_kamen,
                    raidName = Raid.Name.KAMEN,
                    viewModel = kamenVM,
                    onClicked = { viewModel.kamenGoldCalc(it) }
                )
            }
        }
        if (character.checkList.abyssDungeon[1].phases[0].isClear) {
            item {
                val ivoryTowerVM = remember { GoldContentStateVM(character.raidPhaseInfo.ivoryPhase) }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.abyssDungeon[1].phases),
                    raidImg = R.drawable.abyss_dungeon_ivory_tower,
                    raidName = Raid.Name.IVORY_TOWER_LONG,
                    viewModel = ivoryTowerVM,
                    onClicked = { viewModel.iTGoldCalc(it) }
                )
            }
        }
        if (character.checkList.command[4].phases[0].isClear) {
            item {
                val illiakanVM = remember { GoldContentStateVM(character.raidPhaseInfo.illiakanPhase) }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.command[4].phases),
                    raidImg = R.drawable.command_illiakan,
                    raidName = Raid.Name.ILLIAKAN,
                    viewModel = illiakanVM,
                    onClicked = { viewModel.illiGoldCalc(it) }
                )
            }
        }
        if (character.checkList.abyssDungeon[0].phases[0].isClear) {
            item {
                val kayangelVM = remember { GoldContentStateVM(character.raidPhaseInfo.kayangelPhase) }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.abyssDungeon[0].phases),
                    raidImg = R.drawable.abyss_dungeon_kayangel,
                    raidName = Raid.Name.KAYANGEL,
                    viewModel = kayangelVM,
                    onClicked = { viewModel.kayanGoldCalc(it) }
                )
            }
        }
        if (character.checkList.command[3].phases[0].isClear) {
            item {
                val abrelshudVM = remember { GoldContentStateVM(character.raidPhaseInfo.abrelPhase) }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.command[3].phases),
                    raidImg = R.drawable.command_abrelshud,
                    raidName = Raid.Name.ABRELSHUD,
                    viewModel = abrelshudVM,
                    onClicked = { viewModel.abrelGoldCalc(it) }
                )
            }
        }
        if (character.checkList.command[2].phases[0].isClear) {
            item {
                val koukuSatonVM = remember { GoldContentStateVM(character.raidPhaseInfo.koukuPhase) }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.command[2].phases),
                    raidImg = R.drawable.command_kouku,
                    raidName = Raid.Name.KOUKU_SATON,
                    viewModel = koukuSatonVM,
                    onClicked = { viewModel.kokuGoldCalc(it) }
                )
            }
        }
        if (character.checkList.command[1].phases[0].isClear) {
            item {
                val biackissVM = remember { GoldContentStateVM(character.raidPhaseInfo.biackissPhase) }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.command[1].phases),
                    raidImg = R.drawable.command_biackiss,
                    raidName = Raid.Name.BIACKISS,
                    viewModel = biackissVM,
                    onClicked = { viewModel.biaGoldCalc(it) }
                )
            }
        }
        if (character.checkList.command[0].phases[0].isClear) {
            item {
                val valtanVM = remember { GoldContentStateVM(character.raidPhaseInfo.valtanPhase) }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.command[0].phases),
                    raidImg = R.drawable.command_valtan,
                    raidName = Raid.Name.VALTAN,
                    viewModel = valtanVM,
                    onClicked = { viewModel.valGoldCalc(it) }
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProgressState(
    enabled: Boolean,
    phase: Int,
    raidImg: Int,
    raidName: String,
    viewModel: GoldContentStateVM,
    onClicked: (Int) -> Unit
) {
    val textColor = if (raidName == Raid.Name.KAYANGEL) Color.Black else Color.White

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(21f / 9f)
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable(enabled = enabled) {
                onClicked(viewModel.onClicked(phase))
            }
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = raidImg,
            contentDescription = "보스이미지"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = raidName,
                color = textColor,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${viewModel.nowPhase}/${phase} 관문",
                color = textColor,
                fontSize = 12.sp
            )
        }
    }
}