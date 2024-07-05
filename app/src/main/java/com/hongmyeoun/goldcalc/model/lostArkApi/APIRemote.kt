package com.hongmyeoun.goldcalc.model.lostArkApi

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import com.hongmyeoun.goldcalc.R
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
    suspend fun getCharacter(context: Context, characterName: String): Pair<List<CharacterInfo>?, String?> {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

            try {
                val response = lostArkApiService.getCharacters(characterName).execute()
                if (response.isSuccessful) {
                    val characterInfoList = response.body()
                    if (characterInfoList != null) {
                        val primaryCharacter = characterInfoList.find { it.characterName == characterName }
                        val sortedCharacterInfoList = characterInfoList
                            .filter { it.characterName != characterName }
                            .sortedByDescending { parseDoubleWithoutComma(it.itemMaxLevel) }
                            .toMutableList()
                        primaryCharacter?.let { sortedCharacterInfoList.add(0, it) }

                        Pair(sortedCharacterInfoList, null)
                    } else {
                        Pair(null, "\"${characterName}\"(은)는 없는 결과입니다.")
                    }
                } else {
                    // 실패 처리
                    Pair(null, "서버 응답 실패: ${response.code()}")
                }
            } catch (e: IOException) {
                // 네트워크 오류 처리
                Pair(null, "네트워크 오류")
            }
        }
    }

    suspend fun getCharDetail(context: Context, characterName: String): CharacterDetail? {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

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

    suspend fun getCharEquipment(context: Context, characterName: String): List<CharacterItem>? {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

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

    suspend fun getCharGem(context: Context, characterName: String): List<Gem>? {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

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

    suspend fun getCharCard(context: Context, characterName: String): Pair<List<Cards>, List<CardEffects>>? {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

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

    suspend fun getCharSkill(context: Context, characterName: String): List<Skills>? {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

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

    suspend fun getCharEngravings(context: Context, characterName: String): List<SkillEngravings>? {
        return withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(ContextCompat.getString(context, R.string.lost_ark_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authorizationHeader(context))
                .build()

            val lostArkApiService = retrofit.create(LostArkApiService::class.java)

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


    private fun authorizationHeader(context: Context): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", ContextCompat.getString(context, R.string.lost_ark_api))
                .build()
            chain.proceed(request)
        }
        return httpClient.build()
    }
}

fun parseDoubleWithoutComma(value: String): Double {
    return value.replace(",", "").toDouble()
}