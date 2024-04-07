package com.hongmyeoun.goldcalc.view.goldCheck.cardContent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hongmyeoun.goldcalc.view.goldCheck.FourPhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.ThreePhaseBoss
import com.hongmyeoun.goldcalc.view.goldCheck.twoPhaseBoss
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM

@Composable
fun CommandRaid(
    viewModel: CommandBossVM,
) {
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
        twoPhaseBoss(
            name = viewModel.valtan.name,
            totalGold = viewModel.valtan.totalGold,

            phaseOneLevel = viewModel.valtan.onePhase.level,
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

    if (viewModel.biaCheck) {
        twoPhaseBoss(
            name = viewModel.biackiss.name,
            totalGold = viewModel.biackiss.totalGold,

            phaseOneLevel = viewModel.biackiss.onePhase.level,
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

    if (viewModel.koukuCheck) {
        ThreePhaseBoss(
            name = viewModel.koukuSaton.name,
            totalGold = viewModel.koukuSaton.totalGold,

            phaseOneLevel = viewModel.koukuSaton.onePhase.level,
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

    if (viewModel.abreCheck) {
        FourPhaseBoss(
            name = viewModel.abrelshud.name,
            totalGold = viewModel.abrelshud.totalGold,

            phaseOneLevel = viewModel.abrelshud.onePhase.level,
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

    if (viewModel.illiCheck) {
        ThreePhaseBoss(
            name = viewModel.illiakan.name,
            totalGold = viewModel.illiakan.totalGold,

            phaseOneLevel = viewModel.illiakan.onePhase.level,
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

    if (viewModel.kamenCheck) {
        FourPhaseBoss(
            name = viewModel.kamen.name,
            totalGold = viewModel.kamen.totalGold,

            phaseOneLevel = viewModel.kamen.onePhase.level,
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
