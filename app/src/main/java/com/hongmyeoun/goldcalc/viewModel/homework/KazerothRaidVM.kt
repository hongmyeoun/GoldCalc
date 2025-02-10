package com.hongmyeoun.goldcalc.viewModel.homework

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.constants.raid.Raid
import com.hongmyeoun.goldcalc.model.homework.KazerothRaidModel
import com.hongmyeoun.goldcalc.model.imageLoader.FirebaseStorage
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KazerothRaidVM(val character: Character?) : ViewModel() {
    private val kzModel = KazerothRaidModel(character)

    val echidna = kzModel.echidna
    val egir = kzModel.egir
    val abrelshud2 = kzModel.abrelshud
    val mordum = kzModel.mordum

    var totalGold by mutableStateOf(0)

    fun sumGold() {
        echidna.totalGold()
        egir.totalGold()
        abrelshud2.totalGold()
        mordum.totalGold()
        totalGold = echidna.totalGold + egir.totalGold + abrelshud2.totalGold + mordum.totalGold
    }

    init {
        sumGold()
    }

    var echiCheck by mutableStateOf(echidna.isChecked)
    var egirCheck by mutableStateOf(egir.isChecked)
    var abrelCheck by mutableStateOf(abrelshud2.isChecked)
    var mordumCheck by mutableStateOf(mordum.isChecked)

    fun onEchiCheck() {
        echiCheck = !echiCheck
        echidna.onShowChecked()
        sumGold()
    }

    fun onEgirCheck() {
        egirCheck = !egirCheck
        egir.onShowChecked()
        sumGold()
    }

    fun onAbrelCheck() {
        abrelCheck = !abrelCheck
        abrelshud2.onShowChecked()
        sumGold()
    }

    fun onKamenCheck() {
        mordumCheck = !mordumCheck
        mordum.onShowChecked()
        sumGold()
    }

    private val _mainImageUrls = MutableStateFlow<List<String?>>(List(4) { null })
    val mainImageUrls: StateFlow<List<String?>> = _mainImageUrls

    private val _logoImageUrls = MutableStateFlow<List<String?>>(List(4) { null })
    val logoImageUrls: StateFlow<List<String?>> = _logoImageUrls

    fun getImageModel() {
        val raidNames = Raid.Name.KAZEROTH_RAID_LIST
        getImage(raidNames, FirebaseStorage::getRaidMainPath, _mainImageUrls)
        getImage(raidNames, FirebaseStorage::getRaidLogoPath, _logoImageUrls)
    }

    private fun getImage(raidNames: List<String>, pathProvider: (String) -> String, stateFlow: MutableStateFlow<List<String?>>) {
        viewModelScope.launch {
            stateFlow.value = FirebaseStorage.getUrlList(raidNames, pathProvider)
        }
    }
}
