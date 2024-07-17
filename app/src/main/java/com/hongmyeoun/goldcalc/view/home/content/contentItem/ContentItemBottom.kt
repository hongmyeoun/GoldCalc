package com.hongmyeoun.goldcalc.view.home.content.contentItem

import android.app.Activity
import android.content.Context
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.constants.viewConst.Homework.ABREL_4GOLD
import com.hongmyeoun.goldcalc.model.constants.viewConst.Homework.KAMEN_4GOLD
import com.hongmyeoun.goldcalc.model.constants.viewConst.Homework.WEEK_GOLD
import com.hongmyeoun.goldcalc.ui.theme.ImageBG
import com.hongmyeoun.goldcalc.view.profile.normalTextStyle
import com.hongmyeoun.goldcalc.viewModel.home.GoldContentStateVM
import com.hongmyeoun.goldcalc.viewModel.home.HomeContentVM

@Composable
fun HomeworkProgress(
    viewModel: HomeContentVM,
    isListView: Boolean
) {
    val character by viewModel.character.collectAsState()

    val gridCellSize = if (isListView) 2 else 1

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridCellSize), // 열당 x개의 아이템을 보여주도록 설정
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

                val activity = LocalContext.current as? Activity
                val sharedPref = remember { activity?.getPreferences(Context.MODE_PRIVATE) }
                var noReward by remember {
                    val noRewardValue = sharedPref?.getBoolean("${character.name}$KAMEN_4GOLD", false) ?: false
                    mutableStateOf(noRewardValue)
                }

                LaunchedEffect(noReward) {
                    sharedPref?.edit()?.putBoolean("${character.name}$KAMEN_4GOLD", noReward)?.apply()
                }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.command[5].phases),
                    raidImg = R.drawable.command_kamen,
                    raidName = Raid.Name.KAMEN,
                    viewModel = kamenVM,
                    noReward = noReward,
                    rewardCheck = { noReward = it },
                    onClicked = { viewModel.kamenGoldCalc(it, noReward) }
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

                val activity = LocalContext.current as? Activity
                val sharedPref = remember { activity?.getPreferences(Context.MODE_PRIVATE) }
                var noReward by remember {
                    val noRewardValue = sharedPref?.getBoolean("${character.name}$ABREL_4GOLD", false) ?: false
                    mutableStateOf(noRewardValue)
                }

                LaunchedEffect(noReward) {
                    sharedPref?.edit()?.putBoolean("${character.name}$ABREL_4GOLD", noReward)?.apply()
                }

                ProgressState(
                    enabled = viewModel.enabled,
                    phase = viewModel.phaseCalc(character.checkList.command[3].phases),
                    raidImg = R.drawable.command_abrelshud,
                    raidName = Raid.Name.ABRELSHUD,
                    viewModel = abrelshudVM,
                    noReward = noReward,
                    rewardCheck = { noReward = it },
                    onClicked = { viewModel.abrelGoldCalc(it, noReward) }
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
//    isListView: Boolean,
    noReward: Boolean = false,
    rewardCheck: (Boolean) -> Unit = {},
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

        if ((raidName == Raid.Name.ABRELSHUD || raidName == Raid.Name.KAMEN) && phase == 4) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = WEEK_GOLD,
                        style = normalTextStyle(fontSize = 10.sp)
                    )

                    Checkbox(
                        checked = noReward,
                        onCheckedChange = { rewardCheck(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = ImageBG,
                            uncheckedColor = Color.White,
                            checkmarkColor = Color.White
                        )
                    )
                }
            }
        }

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