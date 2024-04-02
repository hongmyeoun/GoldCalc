package com.hongmyeoun.goldcalc.view.goldCheck

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.roomDB.Character
import com.hongmyeoun.goldcalc.model.roomDB.CharacterDB
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.AbyssDungeon
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.CommandRaid
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.EpicRaid
import com.hongmyeoun.goldcalc.view.goldCheck.cardContent.KazerothRaid
import com.hongmyeoun.goldcalc.viewModel.goldCheck.AbyssDungeonVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.CommandBossVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.EpicRaidVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.GoldSettingVM
import com.hongmyeoun.goldcalc.viewModel.goldCheck.KazerothRaidVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Setting(
    charName: String,
    navController: NavHostController,
    viewModel: GoldSettingVM = viewModel(),
    cbVM: CommandBossVM = viewModel(),
    adVM: AbyssDungeonVM = viewModel(),
    kzVM: KazerothRaidVM = viewModel(),
    epVM: EpicRaidVM = viewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = remember { CharacterDB.getDB(context) }
    val dao = db.characterDao()
    val character by dao.getCharacterByName(charName).collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        RaidCard(
            raidType = "군단장 레이드",
            raidImg = Icons.Default.AccountBox,
            totalGold = cbVM.totalGold,
            expanded = cbVM.expanded,
            onArrowClicked = { cbVM.expand() }
        ) {
            CommandRaid(viewModel = cbVM)
        }
        RaidCard(
            raidType = "어비스 던전",
            raidImg = Icons.Default.AccountBox,
            totalGold = adVM.totalGold,
            expanded = adVM.expanded,
            onArrowClicked = { adVM.expand() },
        ) {
            AbyssDungeon(viewModel = adVM)
        }
        RaidCard(
            raidType = "카제로스 레이드",
            raidImg = Icons.Default.AccountBox,
            totalGold = kzVM.totalGold,
            expanded = kzVM.expanded,
            onArrowClicked = { kzVM.expand() }
        ) {
            KazerothRaid(viewModel = kzVM)
        }
        RaidCard(
            raidType = "에픽 레이드",
            raidImg = Icons.Default.AccountBox,
            totalGold = epVM.totalGold,
            expanded = epVM.expanded,
            onArrowClicked = { epVM.expand() }
        ) {
            EpicRaid(viewModel = epVM)
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = viewModel.plusGold,
                onValueChange = { viewModel.plusGoldValue(it) },
                label = { Text(text = "추가 골드") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                )
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = viewModel.minusGold,
                onValueChange = { viewModel.minusGoldValue(it) },
                label = { Text(text = "사용 골드") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                )
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Row {
                Text(text = "주간 수입")
                Text(text = "${viewModel.calcTotalGold(cbVM.totalGold, adVM.totalGold, kzVM.totalGold, epVM.totalGold)}")
            }
            Row {
                OutlinedButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Text(text = "취소")
                }
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            character?.let {
                                val update = character!!.copy(weeklyGold = viewModel.totalGold)
                                dao.update(update)
                            }
                        }
                        navController.popBackStack()
                    }
                ) {
                    Text(text = "완료")
                }

            }
        }
    }
}
