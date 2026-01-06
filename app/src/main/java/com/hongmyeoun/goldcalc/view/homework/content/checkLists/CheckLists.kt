package com.hongmyeoun.goldcalc.view.homework.content.checkLists

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.Labels
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.TitleCard
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents.AbyssDungeon
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents.Command
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents.Epic
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents.Event
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents.Kazeroth
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents.Shadow
import com.hongmyeoun.goldcalc.viewModel.homework.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.homework.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.homework.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.homework.EventRaidVM
import com.hongmyeoun.goldcalc.viewModel.homework.HomeworkVM
import com.hongmyeoun.goldcalc.viewModel.homework.KazerothRaidVM
import com.hongmyeoun.goldcalc.viewModel.homework.ShadowRaidVM

@Composable
fun CheckLists(
    viewModel: HomeworkVM,
    cbVM: CommandBossVM,
    adVM: AbyssDungeonVM,
    kzVM: KazerothRaidVM,
    epVM: EpicRaidVM,
    sdVM: ShadowRaidVM,
    eventVM: EventRaidVM
) {
    Crossfade(
        targetState = viewModel.selectedTab,
        label = Labels.Crossfade.Loading
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
                    TitleCard(
                        raidImg = null,
                        totalGold = sdVM.totalGold,
                    ) {
                        Shadow(viewModel = sdVM)
                    }
                }

                5 -> {
                    ETCGold(
                        viewModel = viewModel,
                        onDone = { viewModel.updateTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold, sdVM.totalGold, eventVM.totalGold) },
                    )
                }

                6 -> {
                    TitleCard(
                        totalGold = eventVM.totalGold
                    ) {
                        Event(viewModel = eventVM)
                    }
                }
            }
        }
    }
}
