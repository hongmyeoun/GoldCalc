package com.hongmyeoun.goldcalc.viewModel.homework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.homework.AbyssDungeonModel
import com.hongmyeoun.goldcalc.model.imageLoader.FirebaseStorage
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AbyssDungeonVM(val character: Character?): ViewModel() {
    private val adModel = AbyssDungeonModel(character)

    val kayangel = adModel.kayangel
    val ivoryTower = adModel.ivoryTower

    var totalGold by mutableStateOf(0)

    fun sumGold(){
        kayangel.totalGold()
        ivoryTower.totalGold()
        totalGold = kayangel.totalGold + ivoryTower.totalGold
    }

    init {
        sumGold()
    }

    var kayangelCheck by mutableStateOf(kayangel.isChecked)
    var ivoryCheck by mutableStateOf(ivoryTower.isChecked)

    fun onKayangelCheck() {
        kayangelCheck = !kayangelCheck
        kayangel.onShowChecked()
        sumGold()
    }

    fun onIvoryCheck() {
        ivoryCheck = !ivoryCheck
        ivoryTower.onShowChecked()
        sumGold()
    }

    private val _mainImageUrls = MutableStateFlow<List<String?>>(List(2) { null })
    val mainImageUrls: StateFlow<List<String?>> = _mainImageUrls

    private val _logoImageUrls = MutableStateFlow<List<String?>>(List(2) { null })
    val logoImageUrls: StateFlow<List<String?>> = _logoImageUrls

    fun getImageModel() {
        val raidNames = Raid.Name.ABYSS_DUNGEON_LIST
        getImage(raidNames, FirebaseStorage.RaidImageLoader::getRaidMainPath, _mainImageUrls)
        getImage(raidNames, FirebaseStorage.RaidImageLoader::getRaidLogoPath, _logoImageUrls)
    }

    private fun getImage(raidNames: List<String>, pathProvider: (String) -> String, stateFlow: MutableStateFlow<List<String?>>) {
        viewModelScope.launch {
            stateFlow.value = FirebaseStorage.getUrlList(raidNames, pathProvider)
        }
    }
}