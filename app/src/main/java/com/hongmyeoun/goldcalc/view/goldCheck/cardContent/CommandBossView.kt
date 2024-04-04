package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hongmyeoun.goldcalc.view.goldCheck.fourPhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.threePhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM

@Composable
fun CommandRaid(
    viewModel: CommandBossVM,
) {

    LaunchedEffect(viewModel.valtanTG, viewModel.biakissTG, viewModel.koukuSatonTG, viewModel.abrelshudTG, viewModel.illiakanTG, viewModel.kamenTG) {
        viewModel.sumGold()
    }

    var valtanCheck by remember { mutableStateOf(false) }
    var biaCheck by remember { mutableStateOf(false) }
    var koukuCheck by remember { mutableStateOf(false) }
    var abreCheck by remember { mutableStateOf(false) }
    var illiCheck by remember { mutableStateOf(false) }
    var kamenCheck by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.weight(1f)) {
            Text(text = "발탄")
            Checkbox(
                checked = valtanCheck,
                onCheckedChange = {
                    valtanCheck = !valtanCheck
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "비아")
            Checkbox(
                checked = biaCheck,
                onCheckedChange = {
                    biaCheck = !biaCheck
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "쿠크")
            Checkbox(
                checked = koukuCheck,
                onCheckedChange = {
                    koukuCheck = !koukuCheck
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "아브")
            Checkbox(
                checked = abreCheck,
                onCheckedChange = {
                    abreCheck = !abreCheck
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "일리")
            Checkbox(
                checked = illiCheck,
                onCheckedChange = {
                    illiCheck = !illiCheck
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "카멘")
            Checkbox(
                checked = kamenCheck,
                onCheckedChange = {
                    kamenCheck = !kamenCheck
                }
            )
        }
    }

    if (valtanCheck) {
        twoPhaseBoss(
            name = viewModel.valtan,
            seeMoreGold = viewModel.valtanSMG,
            clearGold = viewModel.valtanCG,
            totalGold = viewModel.valtanTG,
            onUpdateTotalGoldOnePhase = { updateTotal ->
                viewModel.valtanOnePhase(updateTotal)
            },
            onUpdateTotalGoldTwoPhase = { updateTotal ->
                viewModel.valtanTwoPhase(updateTotal)
            }
        )
    }

    if (biaCheck) {
        twoPhaseBoss(
            name = viewModel.bi,
            seeMoreGold = viewModel.biSMG,
            clearGold = viewModel.biCG,
            totalGold = viewModel.biakissTG,
            onUpdateTotalGoldOnePhase = { updateTotal ->
                viewModel.biakissOnePhase(updateTotal)
            },
            onUpdateTotalGoldTwoPhase = { updateTotal ->
                viewModel.biakissTwoPhase(updateTotal)
            }
        )
    }

    if (koukuCheck) {
        threePhaseBoss(
            name = viewModel.kuku,
            seeMoreGold = viewModel.kukuSMG,
            clearGold = viewModel.kukuCG,
            totalGold = viewModel.koukuSatonTG,
            onUpdateTotalGoldOnePhase = { updateTotal ->
                viewModel.koukuSatonOnePhase(updateTotal)
            },
            onUpdateTotalGoldTwoPhase = { updateTotal ->
                viewModel.koukuSatontwoPhase(updateTotal)
            },
            onUpdateTotalGoldThreePhase = { updateTotal ->
                viewModel.koukuSatonThreePhase(updateTotal)
            }
        )
    }

    if (abreCheck) {
        fourPhaseBoss(
            name = viewModel.abrel,
            seeMoreGold = viewModel.abrelSMG,
            clearGold = viewModel.abrelCG,
            totalGold = viewModel.abrelshudTG,
            onUpdateTotalGoldOnePhase = { updateTotal ->
                viewModel.abrelshudOnePhase(updateTotal)
            },
            onUpdateTotalGoldTwoPhase = { updateTotal ->
                viewModel.abrelshudTwoPhase(updateTotal)
            },
            onUpdateTotalGoldThreePhase = { updateTotal ->
                viewModel.abrelshudThreePhase(updateTotal)
            },
            onUpdateTotalGoldFourPhase = { updateTotal ->
                viewModel.abrelshudFourPhase(updateTotal)
            }
        )
    }

    if (illiCheck) {
        threePhaseBoss(
            name = viewModel.illi,
            seeMoreGold = viewModel.illiSMG,
            clearGold = viewModel.illiCG,
            totalGold = viewModel.illiakanTG,
            onUpdateTotalGoldOnePhase = { updateTotal ->
                viewModel.illiakanOnePhase(updateTotal)
            },
            onUpdateTotalGoldTwoPhase = { updateTotal ->
                viewModel.illiakantwoPhase(updateTotal)
            },
            onUpdateTotalGoldThreePhase = { updateTotal ->
                viewModel.illiakanThreePhase(updateTotal)
            }
        )
    }

    if (kamenCheck) {
        fourPhaseBoss(
            name = viewModel.kamen,
            seeMoreGold = viewModel.kamenSMG,
            clearGold = viewModel.kamenCG,
            totalGold = viewModel.kamenTG,
            onUpdateTotalGoldOnePhase = { updateTotal ->
                viewModel.kamenOnePhase(updateTotal)
            },
            onUpdateTotalGoldTwoPhase = { updateTotal ->
                viewModel.kamentwoPhase(updateTotal)
            },
            onUpdateTotalGoldThreePhase = { updateTotal ->
                viewModel.kamenThreePhase(updateTotal)
            },
            onUpdateTotalGoldFourPhase = { updateTotal ->
                viewModel.kamenFourPhase(updateTotal)
            }
        )
    }

}
