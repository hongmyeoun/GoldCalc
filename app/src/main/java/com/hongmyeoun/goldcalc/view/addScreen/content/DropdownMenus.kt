package com.hongmyeoun.goldcalc.view.addScreen.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import com.hongmyeoun.goldcalc.model.constants.viewConst.AddCharacter
import com.hongmyeoun.goldcalc.ui.theme.DarkGreen
import com.hongmyeoun.goldcalc.view.profile.titleTextStyle
import com.hongmyeoun.goldcalc.viewModel.addScreen.AddScreenVM

@Composable
fun ServerClassSelect(viewModel: AddScreenVM) {
    val serverDropdownExpanded by viewModel.serverDropdownExpanded.collectAsState()
    val serverSelect by viewModel.serverSelect.collectAsState()

    // 서버
    SelectDropdownMenu(
        title = AddCharacter.SERVER,
        dropdownItems = viewModel.serverName,
        dropdownExpand = serverDropdownExpanded,
        selectedStr = serverSelect,
        onButtonClick = { viewModel.onServerDropdownClicked() },
        onDismissClick = { viewModel.onServerDropdownDismiss() },
        onItemClick = { viewModel.onServerSelected(it) }
    )

    val classDropdownExpanded by viewModel.classDropdownExpanded.collectAsState()
    val classSelect by viewModel.classSelect.collectAsState()

    // 직업
    SelectDropdownMenu(
        title = AddCharacter.CLASS,
        dropdownItems = viewModel.className,
        dropdownExpand = classDropdownExpanded,
        selectedStr = classSelect,
        onButtonClick = { viewModel.onClassDropdownClicked() },
        onDismissClick = { viewModel.onClassDropdownDismiss() },
        onItemClick = { viewModel.onClassSelected(it) }
    )
}

@Composable
private fun SelectDropdownMenu(
    title: String,
    dropdownItems: List<String>,
    dropdownExpand: Boolean,
    selectedStr: String,
    onButtonClick: () -> Unit,
    onDismissClick: () -> Unit,
    onItemClick: (String) -> Unit
) {
    var rowSize by remember { mutableStateOf(Size.Zero) }

    Column {
        Text(
            text = title,
            style = titleTextStyle()
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    rowSize = layoutCoordinates.size.toSize()
                }
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen,
                    contentColor = Color.White
                )
            ) {
                Text(text = selectedStr)
            }

            DropdownMenu(
                modifier = Modifier
                    .width(with(LocalDensity.current) { rowSize.width.toDp() })
                    .heightIn(max = if (title == AddCharacter.SERVER) 250.dp else 220.dp),
                expanded = dropdownExpand,
                onDismissRequest = onDismissClick,
                properties = PopupProperties(focusable = true)
            ) {
                dropdownItems.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = item,
                                textAlign = TextAlign.Center
                            )
                        },
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}
