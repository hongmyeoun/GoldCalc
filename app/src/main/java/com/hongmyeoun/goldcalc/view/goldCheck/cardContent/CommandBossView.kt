package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.hongmyeoun.goldcalc.view.goldCheck.fourPhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.threePhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM

@Composable
fun CommandRaid(viewModel: CommandBossVM) {
    val valtan = twoPhaseBoss(
        name = viewModel.valtan,
        seeMoreGold = viewModel.valtanSMG,
        clearGold = viewModel.valtanCG
    )
    val biackiss = twoPhaseBoss(
        name = viewModel.bi,
        seeMoreGold = viewModel.biSMG,
        clearGold = viewModel.biCG
    )
    val koukuSaton = threePhaseBoss(
        name = viewModel.kuku,
        seeMoreGold = viewModel.kukuSMG,
        clearGold = viewModel.kukuCG
    )
    val abrelshud = fourPhaseBoss(
        name = viewModel.abrel,
        seeMoreGold = viewModel.abrelSMG,
        clearGold = viewModel.abrelCG
    )
    val illiakan = threePhaseBoss(
        name = viewModel.illi,
        seeMoreGold = viewModel.illiSMG,
        clearGold = viewModel.illiCG
    )
    val kamen = fourPhaseBoss(
        name = viewModel.kamen,
        seeMoreGold = viewModel.kamenSMG,
        clearGold = viewModel.kamenCG
    )

    LaunchedEffect(valtan, biackiss, koukuSaton, abrelshud, illiakan, kamen) {
        viewModel.sumGold(valtan, biackiss, koukuSaton, abrelshud, illiakan, kamen)
    }

}
