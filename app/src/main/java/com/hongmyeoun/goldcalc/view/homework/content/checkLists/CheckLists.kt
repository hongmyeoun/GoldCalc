package com.hongmyeoun.goldcalc.view.homework.content.checkLists

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.TitleCard
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents.AbyssDungeon
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents.Command
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents.Epic
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents.Kazeroth
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.HomeworkVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM

@Composable
fun CheckLists(
    viewModel: HomeworkVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
) {
    Crossfade(
        targetState = viewModel.selectedTab,
        label = "헤더 Crossfade"
    ) { selectedTab ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (selectedTab) {
                0 -> {
                    TitleCard(
                        raidImg = R.drawable.command_icon,
                        totalGold = cbVM.totalGold
                    ) {
                        Command(viewModel = cbVM)
                    }
                }

                1 -> {
                    TitleCard(
                        raidImg = R.drawable.abyss_dungeon_icon,
                        totalGold = adVM.totalGold,
                    ) {
                        AbyssDungeon(viewModel = adVM)
                    }
                }

                2 -> {
                    TitleCard(
                        raidImg = R.drawable.kazeroth_icon,
                        totalGold = kzVM.totalGold
                    ) {
                        Kazeroth(viewModel = kzVM)
                    }
                }

                3 -> {
                    TitleCard(
                        raidImg = R.drawable.epic_icon,
                        totalGold = epVM.totalGold
                    ) {
                        Epic(viewModel = epVM)
                    }
                }

                4 -> {
                    ETCGold(
                        viewModel = viewModel,
                        onDone = { viewModel.updateTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold) },
                    )
                }
            }
        }
    }
}
