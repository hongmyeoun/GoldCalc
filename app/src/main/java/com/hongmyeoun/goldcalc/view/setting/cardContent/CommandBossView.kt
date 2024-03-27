package com.hongmyeoun.goldcalc.view.setting.cardContent

import androidx.compose.runtime.Composable
import com.hongmyeoun.goldcalc.view.setting.fourPhaseBoss
import com.hongmyeoun.goldcalc.view.setting.threePhaseBoss
import com.hongmyeoun.goldcalc.view.setting.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.CommandBossVM

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

    viewModel.sumGold(valtan, biackiss, koukuSaton, abrelshud, illiakan, kamen)
}
