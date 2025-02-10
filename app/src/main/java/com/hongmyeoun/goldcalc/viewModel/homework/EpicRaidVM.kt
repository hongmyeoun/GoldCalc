package com.hongmyeoun.goldcalc.viewModel.homework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.homework.EpicRaidModel
import com.hongmyeoun.goldcalc.model.imageLoader.FirebaseStorage
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpicRaidVM(val character: Character?): ViewModel() {
    private val epModel = EpicRaidModel(character)

    val behemoth = epModel.behemoth

    var totalGold by mutableStateOf(0)

    fun sumGold() {
        behemoth.totalGold()
        totalGold = behemoth.totalGold
    }

    init {
        sumGold()
    }

    var beheCheck by mutableStateOf(behemoth.isChecked)

    fun onBeheCheck() {
        beheCheck = !beheCheck
        behemoth.onShowChecked()
        sumGold()
    }

    private val _mainImageUrls = MutableStateFlow<List<String?>>(List(1) { null }) // 6개의 이미지 URL을 저장
    val mainImageUrls: StateFlow<List<String?>> = _mainImageUrls
    private val _logoImageUrls = MutableStateFlow<List<String?>>(List(1) { null })
    val logoImageUrls: StateFlow<List<String?>> = _logoImageUrls

    fun getImageModel() {
        val raidNames = Raid.Name.EPIC_RAID_LIST
        getImage(raidNames, FirebaseStorage::getRaidMainPath, _mainImageUrls)
        getImage(raidNames, FirebaseStorage::getRaidLogoPath, _logoImageUrls)
    }

    private fun getImage(raidNames: List<String>, pathProvider: (String) -> String, stateFlow: MutableStateFlow<List<String?>>) {
        viewModelScope.launch {
            stateFlow.value = FirebaseStorage.getUrlList(raidNames, pathProvider)
        }
    }
}