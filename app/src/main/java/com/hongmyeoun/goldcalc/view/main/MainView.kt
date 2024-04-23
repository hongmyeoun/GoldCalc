package com.hongmyeoun.goldcalc.view.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.LoadingScreen
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.view.main.characterCard.CharacterCardTop
import com.hongmyeoun.goldcalc.viewModel.main.CharacterCardVM
import com.hongmyeoun.goldcalc.viewModel.main.CharacterListVM
import com.hongmyeoun.goldcalc.viewModel.main.GoldContentStateVM

@Composable
fun MainScreen(
    characterListVM: CharacterListVM,
    navController: NavHostController,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = { MainAppTopBar(navController, characterListVM) }
    ) {
        content(
            Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun CharacterCard(
    navController: NavHostController,
    viewModel: CharacterCardVM,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val character by viewModel.character.collectAsState()

    if (character.name.isEmpty()) {
        LoadingScreen()
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CharacterCardTop(
                navController = navController,
                viewModel = viewModel,
                onDelete = onDelete
            )
            GoldContents(
                viewModel = viewModel,
                onClick = { onClick() }
            )
        }
    }
}

@Composable
fun GoldContents(
    viewModel: CharacterCardVM,
    onClick: () -> Unit
) {
    val character by viewModel.character.collectAsState()

    if (character.checkList.epic[0].phases[0].isClear) {
        val behemothVM = remember { GoldContentStateVM(character.raidPhaseInfo.behemothPhase) }

        GoldContentStateUI(
            enabled = viewModel.enabled,
            phase = viewModel.phaseCalc(character.checkList.epic[0].phases),
            raidImg = R.drawable.epic_behemoth,
            raidName = "베히모스",
            viewModel = behemothVM,
            onClicked = {
                viewModel.beheGoldCalc(it)
                onClick()
            }
        )
    }
    if (character.checkList.kazeroth[0].phases[0].isClear) {
        val echidnaVM = remember { GoldContentStateVM(character.raidPhaseInfo.echidnaPhase) }

        GoldContentStateUI(
            enabled = viewModel.enabled,
            phase = viewModel.phaseCalc(character.checkList.kazeroth[0].phases),
            raidImg = R.drawable.kazeroth_echidna,
            raidName = "에키드나",
            viewModel = echidnaVM,
            onClicked = {
                viewModel.echiGoldCalc(it)
                onClick()
            }
        )
    }
    if (character.checkList.command[5].phases[0].isClear) {
        val kamenVM = remember { GoldContentStateVM(character.raidPhaseInfo.kamenPhase) }

        GoldContentStateUI(
            enabled = viewModel.enabled,
            phase = viewModel.phaseCalc(character.checkList.command[5].phases),
            raidImg = R.drawable.command_kamen,
            raidName = "카멘",
            viewModel = kamenVM,
            onClicked = {
                viewModel.kamenGoldCalc(it)
                onClick()
            }
        )

    }
    if (character.checkList.abyssDungeon[1].phases[0].isClear) {
        val ivoryTowerVM = remember { GoldContentStateVM(character.raidPhaseInfo.ivoryPhase) }

        GoldContentStateUI(
            enabled = viewModel.enabled,
            phase = viewModel.phaseCalc(character.checkList.abyssDungeon[1].phases),
            raidImg = R.drawable.abyss_dungeon_ivory_tower,
            raidName = "혼돈의 상아탑",
            viewModel = ivoryTowerVM,
            onClicked = {
                viewModel.iTGoldCalc(it)
                onClick()
            }
        )

    }
    if (character.checkList.command[4].phases[0].isClear) {
        val illiakanVM = remember { GoldContentStateVM(character.raidPhaseInfo.illiakanPhase) }

        GoldContentStateUI(
            enabled = viewModel.enabled,
            phase = viewModel.phaseCalc(character.checkList.command[4].phases),
            raidImg = R.drawable.command_illiakan,
            raidName = "일리아칸",
            viewModel = illiakanVM,
            onClicked = {
                viewModel.illiGoldCalc(it)
                onClick()
            }
        )

    }
    if (character.checkList.abyssDungeon[0].phases[0].isClear) {
        val kayangelVM = remember { GoldContentStateVM(character.raidPhaseInfo.kayangelPhase) }

        GoldContentStateUI(
            enabled = viewModel.enabled,
            phase = viewModel.phaseCalc(character.checkList.abyssDungeon[0].phases),
            raidImg = R.drawable.abyss_dungeon_kayangel,
            raidName = "카양겔",
            viewModel = kayangelVM,
            onClicked = {
                viewModel.kayanGoldCalc(it)
                onClick()
            }
        )

    }
    if (character.checkList.command[3].phases[0].isClear) {
        val abrelshudVM = remember { GoldContentStateVM(character.raidPhaseInfo.abrelPhase) }

        GoldContentStateUI(
            enabled = viewModel.enabled,
            phase = viewModel.phaseCalc(character.checkList.command[3].phases),
            raidImg = R.drawable.command_abrelshud,
            raidName = "아브렐슈드",
            viewModel = abrelshudVM,
            onClicked = {
                viewModel.abrelGoldCalc(it)
                onClick()
            }
        )

    }
    if (character.checkList.command[2].phases[0].isClear) {
        val koukuSatonVM = remember { GoldContentStateVM(character.raidPhaseInfo.koukuPhase) }

        GoldContentStateUI(
            enabled = viewModel.enabled,
            phase = viewModel.phaseCalc(character.checkList.command[2].phases),
            raidImg = R.drawable.command_kouku,
            raidName = "쿠크세이튼",
            viewModel = koukuSatonVM,
            onClicked = {
                viewModel.kokuGoldCalc(it)
                onClick()
            }
        )

    }
    if (character.checkList.command[1].phases[0].isClear) {
        val biackissVM = remember { GoldContentStateVM(character.raidPhaseInfo.biackissPhase) }

        GoldContentStateUI(
            enabled = viewModel.enabled,
            phase = viewModel.phaseCalc(character.checkList.command[1].phases),
            raidImg = R.drawable.command_biackiss,
            raidName = "비아키스",
            viewModel = biackissVM,
            onClicked = {
                viewModel.biaGoldCalc(it)
                onClick()
            }
        )

    }
    if (character.checkList.command[0].phases[0].isClear) {
        val valtanVM = remember { GoldContentStateVM(character.raidPhaseInfo.valtanPhase) }

        GoldContentStateUI(
            enabled = viewModel.enabled,
            phase = viewModel.phaseCalc(character.checkList.command[0].phases),
            raidImg = R.drawable.command_valtan,
            raidName = "발탄",
            viewModel = valtanVM,
            onClicked = {
                viewModel.valGoldCalc(it)
                onClick()
            }
        )

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GoldContentStateUI(
    enabled: Boolean,
    phase: Int,
    raidImg: Int,
    raidName: String,
    viewModel: GoldContentStateVM,
    onClicked: (Int) -> Unit
) {
    val textColor = if (raidName == "카양겔") Color.Black else Color.White

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable(enabled = enabled) {
                onClicked(viewModel.onClicked(phase))
            }
    ) {
        GlideImage(
            modifier = Modifier
                .aspectRatio(21f / 9f)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            model = raidImg,
            contentDescription = "보스이미지"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 32.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = raidName,
                color = textColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${viewModel.nowPhase}/${phase} 관문",
                color = textColor
            )
        }
    }
}

//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2), // 열당 2개의 아이템을 보여주도록 설정
//                modifier = Modifier.fillMaxSize() // 전체 공간을 채우도록 설정
//            ) {
//                if (character.checkList.epic[0].phases[0].isClear) {
//                    item {
//                        val behemothVM = remember { GoldContentStateVM(character.raidPhaseInfo.behemothPhase) }
//
//                        GoldContentStateUI(
//                            enabled = viewModel.enabled,
//                            phase = viewModel.phaseCalc(character.checkList.epic[0].phases),
//                            raidImg = R.drawable.epic_behemoth,
//                            raidName = "베히모스",
//                            viewModel = behemothVM,
//                            onClicked = {
//                                viewModel.beheGoldCalc(it)
//                                onClick()
//                            }
//                        )
//                    }
//                }
//                if (character.checkList.kazeroth[0].phases[0].isClear) {
//                    item {
//                        val echidnaVM = remember { GoldContentStateVM(character.raidPhaseInfo.echidnaPhase) }
//
//                        GoldContentStateUI(
//                            enabled = viewModel.enabled,
//                            phase = viewModel.phaseCalc(character.checkList.kazeroth[0].phases),
//                            raidImg = R.drawable.kazeroth_echidna,
//                            raidName = "에키드나",
//                            viewModel = echidnaVM,
//                            onClicked = {
//                                viewModel.echiGoldCalc(it)
//                                onClick()
//                            }
//                        )
//                    }
//                }
//                if (character.checkList.command[5].phases[0].isClear) {
//                    item {
//                        val kamenVM = remember { GoldContentStateVM(character.raidPhaseInfo.kamenPhase) }
//
//                        GoldContentStateUI(
//                            enabled = viewModel.enabled,
//                            phase = viewModel.phaseCalc(character.checkList.command[5].phases),
//                            raidImg = R.drawable.command_kamen,
//                            raidName = "카멘",
//                            viewModel = kamenVM,
//                            onClicked = {
//                                viewModel.kamenGoldCalc(it)
//                                onClick()
//                            }
//                        )
//                    }
//                }
//                if (character.checkList.abyssDungeon[1].phases[0].isClear) {
//                    item {
//                        val ivoryTowerVM = remember { GoldContentStateVM(character.raidPhaseInfo.ivoryPhase) }
//
//                        GoldContentStateUI(
//                            enabled = viewModel.enabled,
//                            phase = viewModel.phaseCalc(character.checkList.abyssDungeon[1].phases),
//                            raidImg = R.drawable.abyss_dungeon_ivory_tower,
//                            raidName = "혼돈의 상아탑",
//                            viewModel = ivoryTowerVM,
//                            onClicked = {
//                                viewModel.iTGoldCalc(it)
//                                onClick()
//                            }
//                        )
//                    }
//                }
//                if (character.checkList.command[4].phases[0].isClear) {
//                    item {
//                        val illiakanVM = remember { GoldContentStateVM(character.raidPhaseInfo.illiakanPhase) }
//
//                        GoldContentStateUI(
//                            enabled = viewModel.enabled,
//                            phase = viewModel.phaseCalc(character.checkList.command[4].phases),
//                            raidImg = R.drawable.command_illiakan,
//                            raidName = "일리아칸",
//                            viewModel = illiakanVM,
//                            onClicked = {
//                                viewModel.illiGoldCalc(it)
//                                onClick()
//                            }
//                        )
//                    }
//                }
//                if (character.checkList.abyssDungeon[0].phases[0].isClear) {
//                    item {
//                        val kayangelVM = remember { GoldContentStateVM(character.raidPhaseInfo.kayangelPhase) }
//
//                        GoldContentStateUI(
//                            enabled = viewModel.enabled,
//                            phase = viewModel.phaseCalc(character.checkList.abyssDungeon[0].phases),
//                            raidImg = R.drawable.abyss_dungeon_kayangel,
//                            raidName = "카양겔",
//                            viewModel = kayangelVM,
//                            onClicked = {
//                                viewModel.kayanGoldCalc(it)
//                                onClick()
//                            }
//                        )
//                    }
//                }
//                if (character.checkList.command[3].phases[0].isClear) {
//                    item {
//                        val abrelshudVM = remember { GoldContentStateVM(character.raidPhaseInfo.abrelPhase) }
//
//                        GoldContentStateUI(
//                            enabled = viewModel.enabled,
//                            phase = viewModel.phaseCalc(character.checkList.command[3].phases),
//                            raidImg = R.drawable.command_abrelshud,
//                            raidName = "아브렐슈드",
//                            viewModel = abrelshudVM,
//                            onClicked = {
//                                viewModel.abrelGoldCalc(it)
//                                onClick()
//                            }
//                        )
//                    }
//                }
//                if (character.checkList.command[2].phases[0].isClear) {
//                    item {
//                        val koukuSatonVM = remember { GoldContentStateVM(character.raidPhaseInfo.koukuPhase) }
//
//                        GoldContentStateUI(
//                            enabled = viewModel.enabled,
//                            phase = viewModel.phaseCalc(character.checkList.command[2].phases),
//                            raidImg = R.drawable.command_kouku,
//                            raidName = "쿠크세이튼",
//                            viewModel = koukuSatonVM,
//                            onClicked = {
//                                viewModel.kokuGoldCalc(it)
//                                onClick()
//                            }
//                        )
//                    }
//                }
//                if (character.checkList.command[1].phases[0].isClear) {
//                    item {
//                        val biackissVM = remember { GoldContentStateVM(character.raidPhaseInfo.biackissPhase) }
//
//                        GoldContentStateUI(
//                            enabled = viewModel.enabled,
//                            phase = viewModel.phaseCalc(character.checkList.command[1].phases),
//                            raidImg = R.drawable.command_biackiss,
//                            raidName = "비아키스",
//                            viewModel = biackissVM,
//                            onClicked = {
//                                viewModel.biaGoldCalc(it)
//                                onClick()
//                            }
//                        )
//                    }
//                }
//                if (character.checkList.command[0].phases[0].isClear) {
//                    item {
//                        val valtanVM = remember { GoldContentStateVM(character.raidPhaseInfo.valtanPhase) }
//
//                        GoldContentStateUI(
//                            enabled = viewModel.enabled,
//                            phase = viewModel.phaseCalc(character.checkList.command[0].phases),
//                            raidImg = R.drawable.command_valtan,
//                            raidName = "발탄",
//                            viewModel = valtanVM,
//                            onClicked = {
//                                viewModel.valGoldCalc(it)
//                                onClick()
//                            }
//                        )
//                    }
//                }
//            }