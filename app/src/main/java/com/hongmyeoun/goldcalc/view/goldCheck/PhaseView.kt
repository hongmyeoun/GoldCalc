package com.hongmyeoun.goldcalc.view.goldCheck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PhaseUI(
    phase: Int,
    difficulty: String,
    clearCheck: Boolean,
    moreCheck: Boolean,
    onLevelClicked: () -> Unit,
    onClearClicked: (Boolean) -> Unit,
    onMoreClicked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(3f)
                .padding(start = 16.dp),
            text = "$phase 관문"
        )
        OutlinedButton(
            onClick = { onLevelClicked() },
            enabled = clearCheck || moreCheck
        ) {
            Text(text = difficulty)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = clearCheck,
                onCheckedChange = { onClearClicked(it) }
            )
            Text(text = "클리어")
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = moreCheck,
                onCheckedChange = { onMoreClicked(it) }
            )
            Text(text = "더보기")
        }
    }

}

@Composable
fun twoPhaseBoss(
    name: String,
    totalGold: Int,

    phaseOneLevel: String,
    phaseOneSMC: Boolean,
    phaseOneCC: Boolean,
    onOnePhaseLevelClicked: () -> Unit,
    onOnePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onOnePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseTwoLevel: String,
    phaseTwoSMC: Boolean,
    phaseTwoCC: Boolean,
    onTwoPhaseLevelClicked: () -> Unit,
    onTwoPhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onTwoPhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "보스 이미지")
            Text(
                modifier = Modifier.weight(2f),
                text = name
            )
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "$totalGold")
            }
        }

        PhaseUI(
            phase = 1,
            difficulty = phaseOneLevel,
            clearCheck = phaseOneCC,
            moreCheck = phaseOneSMC,
            onLevelClicked = { onOnePhaseLevelClicked() },
            onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
            onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
        )

        PhaseUI(
            phase = 2,
            difficulty = phaseTwoLevel,
            clearCheck = phaseTwoCC,
            moreCheck = phaseTwoSMC,
            onLevelClicked = { onTwoPhaseLevelClicked() },
            onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
            onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
        )

    }
}

@Composable
fun ThreePhaseBoss(
    name: String,
    totalGold: Int,

    phaseOneLevel: String,
    phaseOneSMC: Boolean,
    phaseOneCC: Boolean,
    onOnePhaseLevelClicked: () -> Unit,
    onOnePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onOnePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseTwoLevel: String,
    phaseTwoSMC: Boolean,
    phaseTwoCC: Boolean,
    onTwoPhaseLevelClicked: () -> Unit,
    onTwoPhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onTwoPhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseThreeLevel: String,
    phaseThreeSMC: Boolean,
    phaseThreeCC: Boolean,
    onThreePhaseLevelClicked: () -> Unit,
    onThreePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onThreePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "보스 이미지")
            Text(
                modifier = Modifier.weight(2f),
                text = name
            )
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "$totalGold")
            }
        }

        PhaseUI(
            phase = 1,
            difficulty = phaseOneLevel,
            clearCheck = phaseOneCC,
            moreCheck = phaseOneSMC,
            onLevelClicked = { onOnePhaseLevelClicked() },
            onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
            onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
        )

        PhaseUI(
            phase = 2,
            difficulty = phaseTwoLevel,
            clearCheck = phaseTwoCC,
            moreCheck = phaseTwoSMC,
            onLevelClicked = { onTwoPhaseLevelClicked() },
            onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
            onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
        )

        PhaseUI(
            phase = 3,
            difficulty = phaseThreeLevel,
            clearCheck = phaseThreeCC,
            moreCheck = phaseThreeSMC,
            onLevelClicked = { onThreePhaseLevelClicked() },
            onClearClicked = { onThreePhaseClearCheckBoxChecked(it) },
            onMoreClicked = { onThreePhaseSeeMoreCheckBoxChecked(it) }
        )

    }
}

@Composable
fun FourPhaseBoss(
    name: String,
    totalGold: Int,

    phaseOneLevel: String,
    phaseOneSMC: Boolean,
    phaseOneCC: Boolean,
    onOnePhaseLevelClicked: () -> Unit,
    onOnePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onOnePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseTwoLevel: String,
    phaseTwoSMC: Boolean,
    phaseTwoCC: Boolean,
    onTwoPhaseLevelClicked: () -> Unit,
    onTwoPhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onTwoPhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseThreeLevel: String,
    phaseThreeSMC: Boolean,
    phaseThreeCC: Boolean,
    onThreePhaseLevelClicked: () -> Unit,
    onThreePhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onThreePhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,

    phaseFourLevel: String,
    phaseFourSMC: Boolean,
    phaseFourCC: Boolean,
    onFourPhaseLevelClicked: () -> Unit,
    onFourPhaseClearCheckBoxChecked: (Boolean) -> Unit,
    onFourPhaseSeeMoreCheckBoxChecked: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "보스 이미지")
            Text(
                modifier = Modifier.weight(2f),
                text = name
            )
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "골드 이미지")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "$totalGold")
            }
        }

        PhaseUI(
            phase = 1,
            difficulty = phaseOneLevel,
            clearCheck = phaseOneCC,
            moreCheck = phaseOneSMC,
            onLevelClicked = { onOnePhaseLevelClicked() },
            onClearClicked = { onOnePhaseClearCheckBoxChecked(it) },
            onMoreClicked = { onOnePhaseSeeMoreCheckBoxChecked(it) }
        )

        PhaseUI(
            phase = 2,
            difficulty = phaseTwoLevel,
            clearCheck = phaseTwoCC,
            moreCheck = phaseTwoSMC,
            onLevelClicked = { onTwoPhaseLevelClicked() },
            onClearClicked = { onTwoPhaseClearCheckBoxChecked(it) },
            onMoreClicked = { onTwoPhaseSeeMoreCheckBoxChecked(it) }
        )

        PhaseUI(
            phase = 3,
            difficulty = phaseThreeLevel,
            clearCheck = phaseThreeCC,
            moreCheck = phaseThreeSMC,
            onLevelClicked = { onThreePhaseLevelClicked() },
            onClearClicked = { onThreePhaseClearCheckBoxChecked(it) },
            onMoreClicked = { onThreePhaseSeeMoreCheckBoxChecked(it) }
        )

        PhaseUI(
            phase = 4,
            difficulty = phaseFourLevel,
            clearCheck = phaseFourCC,
            moreCheck = phaseFourSMC,
            onLevelClicked = { onFourPhaseLevelClicked() },
            onClearClicked = { onFourPhaseClearCheckBoxChecked(it) },
            onMoreClicked = { onFourPhaseSeeMoreCheckBoxChecked(it) }
        )
    }
}
