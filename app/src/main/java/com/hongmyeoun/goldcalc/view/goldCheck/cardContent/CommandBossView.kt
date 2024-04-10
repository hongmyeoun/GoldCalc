package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hongmyeoun.goldcalc.R
import com.hongmyeoun.goldcalc.view.goldCheck.FourPhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.ThreePhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.TwoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM

@Composable
fun CommandRaid(
    viewModel: CommandBossVM,
) {
    var valtanRotated by remember { mutableStateOf(false) }
    var biackissRotated by remember { mutableStateOf(false) }
    var koukuSatonRotated by remember { mutableStateOf(false) }
    var abrelshudRotated by remember { mutableStateOf(false) }
    var illiakanRotated by remember { mutableStateOf(false) }
    var kamenRotated by remember { mutableStateOf(false) }

    val valtanRotaR by animateFloatAsState(
        targetValue = if (valtanRotated) 180f else 0f,
        animationSpec = tween(500)
    )
    val biackissRotaR by animateFloatAsState(
        targetValue = if (biackissRotated) 180f else 0f,
        animationSpec = tween(500)
    )
    val koukuSatonRotaR by animateFloatAsState(
        targetValue = if (koukuSatonRotated) 180f else 0f,
        animationSpec = tween(500)
    )
    val abrelshudRotaR by animateFloatAsState(
        targetValue = if (abrelshudRotated) 180f else 0f,
        animationSpec = tween(500)
    )
    val illiakanRotaR by animateFloatAsState(
        targetValue = if (illiakanRotated) 180f else 0f,
        animationSpec = tween(500)
    )
    val kamenRotaR by animateFloatAsState(
        targetValue = if (kamenRotated) 180f else 0f,
        animationSpec = tween(500)
    )

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(text = "발탄")
            Checkbox(
                checked = viewModel.valtanCheck,
                onCheckedChange = {
                    viewModel.onValtanCheck()
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "비아")
            Checkbox(
                checked = viewModel.biaCheck,
                onCheckedChange = {
                    viewModel.onBiaCheck()
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "쿠크")
            Checkbox(
                checked = viewModel.koukuCheck,
                onCheckedChange = {
                    viewModel.onKoukuCheck()
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "아브")
            Checkbox(
                checked = viewModel.abreCheck,
                onCheckedChange = {
                    viewModel.onAbreCheck()
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "일리")
            Checkbox(
                checked = viewModel.illiCheck,
                onCheckedChange = {
                    viewModel.onIlliCheck()
                }
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            Text(text = "카멘")
            Checkbox(
                checked = viewModel.kamenCheck,
                onCheckedChange = {
                    viewModel.onKamenCheck()
                }
            )
        }
    }

    if (viewModel.valtanCheck) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = valtanRotaR
                    cameraDistance = 8 * density
                }
                .clickable { valtanRotated = !valtanRotated }
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            if (!valtanRotated) {
                Image(
                    modifier = Modifier.aspectRatio(21f/9f),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.command_valtan),
                    contentDescription = "발탄"
                )
            } else {
                TwoPhaseBoss(
                    rotaR = valtanRotaR,

                    name = viewModel.valtan.name,
                    raidBossImg = R.drawable.logo_valtan,
                    totalGold = viewModel.valtan.totalGold,

                    phaseOneLevel = viewModel.valtan.onePhase.level,
                    phaseOneGold = viewModel.valtan.onePhase.totalGold,
                    phaseOneSMC = viewModel.valtan.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.valtan.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.valtan.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.valtan.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.valtan.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.valtan.twoPhase.level,
                    phaseTwoGold = viewModel.valtan.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.valtan.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.valtan.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.valtan.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.valtan.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.valtan.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                )

            }
        }
    }

    if (viewModel.biaCheck) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = biackissRotaR
                    cameraDistance = 8 * density
                }
                .clickable { biackissRotated = !biackissRotated }
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            if (!biackissRotated) {
                Image(
                    modifier = Modifier.aspectRatio(21f/9f),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.command_biackiss),
                    contentDescription = "비아키스"
                )
            } else {
                TwoPhaseBoss(
                    rotaR = biackissRotaR,

                    name = viewModel.biackiss.name,
                    raidBossImg = R.drawable.logo_biackiss,
                    totalGold = viewModel.biackiss.totalGold,

                    phaseOneLevel = viewModel.biackiss.onePhase.level,
                    phaseOneGold = viewModel.biackiss.onePhase.totalGold,
                    phaseOneSMC = viewModel.biackiss.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.biackiss.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.biackiss.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.biackiss.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.biackiss.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.biackiss.twoPhase.level,
                    phaseTwoGold = viewModel.biackiss.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.biackiss.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.biackiss.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.biackiss.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.biackiss.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.biackiss.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                )

            }
        }

    }

    if (viewModel.koukuCheck) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = koukuSatonRotaR
                    cameraDistance = 8 * density
                }
                .clickable { koukuSatonRotated = !koukuSatonRotated }
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            if (!koukuSatonRotated) {
                Image(
                    modifier = Modifier.aspectRatio(21f/9f),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.command_kouku),
                    contentDescription = "쿠크세이튼"
                )
            } else {
                ThreePhaseBoss(
                    rotaR = koukuSatonRotaR,

                    name = viewModel.koukuSaton.name,
                    raidBossImg = R.drawable.logo_saton,
                    totalGold = viewModel.koukuSaton.totalGold,

                    phaseOneLevel = viewModel.koukuSaton.onePhase.level,
                    phaseOneGold = viewModel.koukuSaton.onePhase.totalGold,
                    phaseOneSMC = viewModel.koukuSaton.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.koukuSaton.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.koukuSaton.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.koukuSaton.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.koukuSaton.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.koukuSaton.twoPhase.level,
                    phaseTwoGold = viewModel.koukuSaton.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.koukuSaton.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.koukuSaton.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.koukuSaton.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.koukuSaton.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.koukuSaton.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseThreeLevel = viewModel.koukuSaton.threePhase.level,
                    phaseThreeGold = viewModel.koukuSaton.threePhase.totalGold,
                    phaseThreeSMC = viewModel.koukuSaton.threePhase.seeMoreCheck,
                    phaseThreeCC = viewModel.koukuSaton.threePhase.clearCheck,
                    onThreePhaseLevelClicked = {
                        viewModel.koukuSaton.threePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onThreePhaseClearCheckBoxChecked = {
                        viewModel.koukuSaton.threePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onThreePhaseSeeMoreCheckBoxChecked = {
                        viewModel.koukuSaton.threePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    }
                )

            }
        }

    }

    if (viewModel.abreCheck) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = abrelshudRotaR
                    cameraDistance = 8 * density
                }
                .clickable { abrelshudRotated = !abrelshudRotated }
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            if (!abrelshudRotated) {
                Image(
                    modifier = Modifier.aspectRatio(21f/9f),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.command_abrelshud),
                    contentDescription = "아브렐슈드"
                )
            } else {
                FourPhaseBoss(
                    rotaR = abrelshudRotaR,

                    name = viewModel.abrelshud.name,
                    raidBossImg = R.drawable.logo_abrelshud,
                    totalGold = viewModel.abrelshud.totalGold,

                    phaseOneLevel = viewModel.abrelshud.onePhase.level,
                    phaseOneGold = viewModel.abrelshud.onePhase.totalGold,
                    phaseOneSMC = viewModel.abrelshud.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.abrelshud.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.abrelshud.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.abrelshud.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.abrelshud.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.abrelshud.twoPhase.level,
                    phaseTwoGold = viewModel.abrelshud.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.abrelshud.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.abrelshud.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.abrelshud.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.abrelshud.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.abrelshud.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseThreeLevel = viewModel.abrelshud.threePhase.level,
                    phaseThreeGold = viewModel.abrelshud.threePhase.totalGold,
                    phaseThreeSMC = viewModel.abrelshud.threePhase.seeMoreCheck,
                    phaseThreeCC = viewModel.abrelshud.threePhase.clearCheck,
                    onThreePhaseLevelClicked = {
                        viewModel.abrelshud.threePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onThreePhaseClearCheckBoxChecked = {
                        viewModel.abrelshud.threePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onThreePhaseSeeMoreCheckBoxChecked = {
                        viewModel.abrelshud.threePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseFourLevel = viewModel.abrelshud.fourPhase.level,
                    phaseFourGold = viewModel.abrelshud.fourPhase.totalGold,
                    phaseFourSMC = viewModel.abrelshud.fourPhase.seeMoreCheck,
                    phaseFourCC = viewModel.abrelshud.fourPhase.clearCheck,
                    onFourPhaseLevelClicked = {
                        viewModel.abrelshud.fourPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onFourPhaseClearCheckBoxChecked = {
                        viewModel.abrelshud.fourPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onFourPhaseSeeMoreCheckBoxChecked = {
                        viewModel.abrelshud.fourPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    }
                )

            }
        }

    }

    if (viewModel.illiCheck) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = illiakanRotaR
                    cameraDistance = 8 * density
                }
                .clickable { illiakanRotated = !illiakanRotated }
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            if (!illiakanRotated) {
                Image(
                    modifier = Modifier.aspectRatio(21f/9f),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.command_illiakan),
                    contentDescription = "일리아칸"
                )
            } else {
                ThreePhaseBoss(
                    rotaR = illiakanRotaR,

                    name = viewModel.illiakan.name,
                    raidBossImg = R.drawable.logo_illiakan,
                    totalGold = viewModel.illiakan.totalGold,

                    phaseOneLevel = viewModel.illiakan.onePhase.level,
                    phaseOneGold = viewModel.illiakan.onePhase.totalGold,
                    phaseOneSMC = viewModel.illiakan.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.illiakan.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.illiakan.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.illiakan.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.illiakan.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.illiakan.twoPhase.level,
                    phaseTwoGold = viewModel.illiakan.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.illiakan.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.illiakan.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.illiakan.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.illiakan.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.illiakan.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseThreeLevel = viewModel.illiakan.threePhase.level,
                    phaseThreeGold = viewModel.illiakan.threePhase.totalGold,
                    phaseThreeSMC = viewModel.illiakan.threePhase.seeMoreCheck,
                    phaseThreeCC = viewModel.illiakan.threePhase.clearCheck,
                    onThreePhaseLevelClicked = {
                        viewModel.illiakan.threePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onThreePhaseClearCheckBoxChecked = {
                        viewModel.illiakan.threePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onThreePhaseSeeMoreCheckBoxChecked = {
                        viewModel.illiakan.threePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    }
                )

            }
        }

    }

    if (viewModel.kamenCheck) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    rotationY = kamenRotaR
                    cameraDistance = 8 * density
                }
                .clickable { kamenRotated = !kamenRotated }
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            if (!kamenRotated) {
                Image(
                    painter = painterResource(id = R.drawable.command_kamen),
                    contentDescription = "카멘"
                )
            } else {
                FourPhaseBoss(
                    rotaR = kamenRotaR,

                    name = viewModel.kamen.name,
                    raidBossImg = R.drawable.logo_kamen,
                    totalGold = viewModel.kamen.totalGold,

                    phaseOneLevel = viewModel.kamen.onePhase.level,
                    phaseOneGold = viewModel.kamen.onePhase.totalGold,
                    phaseOneSMC = viewModel.kamen.onePhase.seeMoreCheck,
                    phaseOneCC = viewModel.kamen.onePhase.clearCheck,
                    onOnePhaseLevelClicked = {
                        viewModel.kamen.onePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onOnePhaseClearCheckBoxChecked = {
                        viewModel.kamen.onePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onOnePhaseSeeMoreCheckBoxChecked = {
                        viewModel.kamen.onePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseTwoLevel = viewModel.kamen.twoPhase.level,
                    phaseTwoGold = viewModel.kamen.twoPhase.totalGold,
                    phaseTwoSMC = viewModel.kamen.twoPhase.seeMoreCheck,
                    phaseTwoCC = viewModel.kamen.twoPhase.clearCheck,
                    onTwoPhaseLevelClicked = {
                        viewModel.kamen.twoPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onTwoPhaseClearCheckBoxChecked = {
                        viewModel.kamen.twoPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onTwoPhaseSeeMoreCheckBoxChecked = {
                        viewModel.kamen.twoPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseThreeLevel = viewModel.kamen.threePhase.level,
                    phaseThreeGold = viewModel.kamen.threePhase.totalGold,
                    phaseThreeSMC = viewModel.kamen.threePhase.seeMoreCheck,
                    phaseThreeCC = viewModel.kamen.threePhase.clearCheck,
                    onThreePhaseLevelClicked = {
                        viewModel.kamen.threePhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onThreePhaseClearCheckBoxChecked = {
                        viewModel.kamen.threePhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onThreePhaseSeeMoreCheckBoxChecked = {
                        viewModel.kamen.threePhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    },

                    phaseFourLevel = viewModel.kamen.fourPhase.level,
                    phaseFourGold = viewModel.kamen.fourPhase.totalGold,
                    phaseFourSMC = viewModel.kamen.fourPhase.seeMoreCheck,
                    phaseFourCC = viewModel.kamen.fourPhase.clearCheck,
                    onFourPhaseLevelClicked = {
                        viewModel.kamen.fourPhase.onLevelClicked()
                        viewModel.sumGold()
                    },
                    onFourPhaseClearCheckBoxChecked = {
                        viewModel.kamen.fourPhase.onClearCheckBoxClicked(it)
                        viewModel.sumGold()
                    },
                    onFourPhaseSeeMoreCheckBoxChecked = {
                        viewModel.kamen.fourPhase.onSeeMoreCheckBoxClicked(it)
                        viewModel.sumGold()
                    }
                )

            }
        }

    }

}
