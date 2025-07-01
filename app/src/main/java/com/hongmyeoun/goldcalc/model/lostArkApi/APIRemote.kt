package com.hongmyeoun.goldcalc.model.lostArkApi

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hongmyeoun.goldcalc.BuildConfig
import com.hongmyeoun.goldcalc.model.constants.ErrorMessage
import com.hongmyeoun.goldcalc.model.constants.NetworkConfig
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassive
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassiveDetail
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassiveNode
import com.hongmyeoun.goldcalc.model.profile.card.CardDetail
import com.hongmyeoun.goldcalc.model.profile.card.CardEffects
import com.hongmyeoun.goldcalc.model.profile.card.Cards
import com.hongmyeoun.goldcalc.model.profile.engravings.SkillEngravings
import com.hongmyeoun.goldcalc.model.profile.engravings.SkillEngravingsDetail
import com.hongmyeoun.goldcalc.model.profile.equipment.CharacterItem
import com.hongmyeoun.goldcalc.model.profile.equipment.EquipmentDetail
import com.hongmyeoun.goldcalc.model.profile.gem.Gem
import com.hongmyeoun.goldcalc.model.profile.gem.GemDetail
import com.hongmyeoun.goldcalc.model.profile.skills.CombatSkillsDetail
import com.hongmyeoun.goldcalc.model.profile.skills.Skills
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object APIRemote {
    private const val API_KEY = BuildConfig.API_KEY
    private const val BASE_URL = NetworkConfig.DEV_URL

    val gson: Gson = GsonBuilder().setLenient().create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(authorizationHeader())
        .build()

    private val lostArkApiService: LostArkApiService = retrofit.create(LostArkApiService::class.java)

    suspend fun getCharacter(characterName: String): Pair<List<SearchedCharacter>?, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = lostArkApiService.getCharacters(characterName).execute()
                if (response.isSuccessful) {
                    val characterInfoList = response.body()
                    if (characterInfoList != null) {
                        val primaryCharacter = characterInfoList.find { it.characterName == characterName }
                        val sortedCharacterInfoList = characterInfoList
                            .filter { it.characterName != characterName }
                            .sortedByDescending { parseDoubleWithoutComma(it.itemAvgLevel) }
                            .toMutableList()
                        primaryCharacter?.let { sortedCharacterInfoList.add(0, it) }

                        Pair(sortedCharacterInfoList, null)
                    } else {
                        Pair(null, ErrorMessage.noResult(characterName))
                    }
                } else {
                    // 실패 처리
                    Pair(null, ErrorMessage.serverError(response.code()))
                }
            } catch (e: IOException) {
                // 네트워크 오류 처리
                Pair(null, NetworkConfig.NETWORK_ERROR)
            }
        }
    }

    suspend fun getCharDetail(characterName: String): SearchedCharacterDetail? {
        return withContext(Dispatchers.IO) {
            try {
                val characterInfo = lostArkApiService.getCharacters(characterName).execute()
                if (characterInfo.isSuccessful) {
                    val characterInfoList = characterInfo.body()
                    val char = characterInfoList?.find { it.characterName == characterName }
                    if (characterInfoList != null) {
                        val profilesResponse = lostArkApiService.getCharacterDetail(characterName).execute()
                        if (profilesResponse.isSuccessful) {
                            val charDetail = profilesResponse.body()

                            charDetail?.serverName = char!!.serverName

                            charDetail
                        } else{
                            null
                        }
                    } else {
                        null
                    }
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    suspend fun getCharEquipment(characterName: String): List<CharacterItem>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = lostArkApiService.getCharacterEquipment(characterName).execute()
                if (response.isSuccessful) {
                    val equipmentList = response.body()
                    equipmentList?.let {
                        val characterEquipmentDetail = EquipmentDetail(it)
                        return@withContext characterEquipmentDetail.getCharacterEquipmentDetails()
                    }
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    suspend fun getCharGem(characterName: String): List<Gem>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = lostArkApiService.getCharacterGem(characterName).execute()
                if (response.isSuccessful) {
                    val gemList = response.body()
                    gemList?.let {
                        val characterGemDetail = GemDetail(it)
                        val gems = characterGemDetail.getCharacterGemDetails()
                        return@withContext gems.sortedByDescending<Gem, Int> { gem -> gem.level }
                    }
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    suspend fun getCharCard(characterName: String): Pair<List<Cards>, List<CardEffects>>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = lostArkApiService.getCharacterCard(characterName).execute()
                if (response.isSuccessful) {
                    val gemList = response.body()
                    gemList?.let {
                        val cardDetail = CardDetail(it)
                        val cards = cardDetail.getCardsDetails()
                        val effects = cardDetail.getCardEffects()
                        return@withContext Pair(cards, effects)
                    }
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    suspend fun getCharSkill(characterName: String): List<Skills>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = lostArkApiService.getCharacterSkills(characterName).execute()
                if (response.isSuccessful) {
                    val gemList = response.body()
                    gemList?.let {
                        val characterCombatSkills = CombatSkillsDetail(it)
                        val skills = characterCombatSkills.getSkills()
                        return@withContext skills.sortedByDescending { item -> item.level }
                    }
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    suspend fun getCharEngravings(characterName: String): List<SkillEngravings>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = lostArkApiService.getCharacterEngravings(characterName).execute()
                if (response.isSuccessful) {
                    val engravings = response.body()
                    engravings?.let {
                        val skillEngravingDetail = SkillEngravingsDetail(engravings)
                        val detailedEngravings = skillEngravingDetail.getEngravingsDetail()
                        return@withContext detailedEngravings.sortedWith(compareByDescending<SkillEngravings> { it.awakenEngravingsPoint != null }.thenBy { it.awakenEngravingsPoint })
                    }
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    suspend fun getCharArkPassive(characterName: String): ArkPassive? {
        return withContext(Dispatchers.IO) {
            try {
                val response = lostArkApiService.getCharacterArkPassive(characterName).execute()
                if (response.isSuccessful) {
                    val arkPassive = response.body()
                    arkPassive?.let {
                        return@withContext it
                    }
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    suspend fun getCharArkPassiveNode(characterName: String): List<ArkPassiveNode>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = lostArkApiService.getCharacterArkPassive(characterName).execute()
                if (response.isSuccessful) {
                    val arkPassive = response.body()
                    arkPassive?.let {
                        if (arkPassive.effects.isNotEmpty()) {
                            val arkPassiveDetail = ArkPassiveDetail(arkPassive)

                            return@withContext arkPassiveDetail.getArkPassiveDetail()
                        } else {
                            return@withContext null
                        }
                    }
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            }
        }
    }

    private fun authorizationHeader(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(NetworkConfig.API_HEADER, API_KEY)
                .build()
            chain.proceed(request)
        }
        return httpClient.build()
    }
    private fun parseDoubleWithoutComma(value: String): Double {
        return value.replace(",", "").toDouble()
    }
}

