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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

    private val _imageUrls = MutableStateFlow<List<String?>>(List(1) { null }) // 6개의 이미지 URL을 저장
    val imageUrls: StateFlow<List<String?>> = _imageUrls

    fun getImageModel() {
        val raidNames = Raid.Name.EPIC_RAID_LIST

        viewModelScope.launch {
            val urls = raidNames.map { raidName ->
                val imagePath = FirebaseStorage.getImagePath(raidName)
                suspendCoroutine { continuation ->
                    FirebaseStorage.getFirebaseImageUrl(imagePath) { url ->
                        continuation.resume(url)
                    }
                }
            }
            _imageUrls.value = urls
        }
    }
}