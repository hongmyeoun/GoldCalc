package com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.raidContents

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.model.constants.Labels
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.RaidCard
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.RaidCheckBox
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.RaidCheckLists
import com.hongmyeoun.goldcalc.view.homework.content.checkLists.raidCard.phase.OnePhase
import com.hongmyeoun.goldcalc.viewModel.homework.EventRaidVM

@Composable
fun Event(viewModel: EventRaidVM) {
    var eventRotated by remember { mutableStateOf(false) }
    val eventRotaR by animateFloatAsState(
        targetValue = if (eventRotated) 180f else 0f,
        animationSpec = tween(500),
        label = Labels.Animation.ROTATION
    )

    RaidCheckLists(maxItem = 2) { modifier ->
        RaidCheckBox(
            name = Raid.Name.EVENT_RAID,
            modifier = modifier,
            checked = viewModel.eventCheck,
            onCheckedChange = { viewModel.onEventCheck() }
        )
    }

    if (viewModel.eventCheck) {
        RaidCard(
            bossImg = R.drawable.event_kamen,
            isRotated = eventRotated,
            rotaR = eventRotaR,
            onClick = { eventRotated = !eventRotated },
            phaseCard = {
                OnePhase(
                    rotaR = eventRotaR,

                    name = viewModel.event.name,
                    raidBossImg = R.drawable.logo_kamen,
                    totalGold = viewModel.event.totalGold,

                    phaseOneLevel = viewModel.event.onePhase.level,
                    phaseOneGold = viewModel.event.onePhase.totalGold,
                    phaseOneSMC = viewModel.event.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.event.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.event.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.event.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.event.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    }
                )
            }
        )
    }
}