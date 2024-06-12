package com.hongmyeoun.goldcalc.viewModel.charDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongmyeoun.goldcalc.model.lostArkApi.APIRemote
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharDetailVM @Inject constructor(
    private val characterRepository: CharacterRepository,
): ViewModel() {
    // DB에 저장되어 있는지 여부
    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved

    // DB에 나열된 이름에서 포함되는지 파악(Unique key)
    fun isSavedName(charName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedNames = withContext(Dispatchers.IO) { characterRepository.getNames() }
            _isSaved.value = savedNames.contains(charName)
        }
    }



    // API로 받아올 캐릭터 형식
    private val _characterDetail = MutableStateFlow<CharacterDetail?>(null)
    val characterDetail: StateFlow<CharacterDetail?> = _characterDetail

    // API에서 받아옴
    fun getCharDetail(context: Context, charName: String) {
        viewModelScope.launch {
            _characterDetail.value = APIRemote.getCharDetail(context, charName)
        }
    }

    // 버튼을 눌렀을시 character를 DB에 저장
    fun saveCharDetailToLocal(charDetail: CharacterDetail) {
        val avatarImage = !charDetail.characterImage.isNullOrEmpty() // 접속여부가 없다면 이미지가 null값임, 객체에서는 not null이기 때문에 경고가 뜸

        val character = Character(
            name = charDetail.characterName,
            itemLevel = charDetail.itemMaxLevel,
            serverName = charDetail.serverName,
            className = charDetail.characterClassName,
            guildName = charDetail.guildName,
            title = charDetail.title,
            characterLevel = charDetail.characterLevel,
            expeditionLevel = charDetail.expeditionLevel,
            pvpGradeName = charDetail.pvpGradeName,
            townLevel = charDetail.townLevel,
            townName = charDetail.townName,
            characterImage = charDetail.characterImage,
            avatarImage = avatarImage
        )
        saveCharacter(character)
    }

    // DB에 저장, 동시에 저장여부를 true로 바꾸면서 UI에서 버튼을 없에버림
    private fun saveCharacter(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.insertAll(character)
        }
        _isSaved.value = true
    }
}