package com.hongmyeoun.goldcalc.model.lostArkApi

import com.hongmyeoun.goldcalc.model.constants.NetworkConfig
import com.hongmyeoun.goldcalc.model.profile.arkpassive.ArkPassive
import com.hongmyeoun.goldcalc.model.profile.card.CardsWithEffects
import com.hongmyeoun.goldcalc.model.profile.engravings.SkillEngravingsAndEffects
import com.hongmyeoun.goldcalc.model.profile.equipment.Equipment
import com.hongmyeoun.goldcalc.model.profile.gem.GemAndEffect
import com.hongmyeoun.goldcalc.model.profile.skills.CombatSkills
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LostArkApiService {
    @GET(NetworkConfig.SIBLINGS)
    fun getCharacters(
        @Path(NetworkConfig.SEARCH_PATH) characterName: String
    ): Call<List<SearchedCharacter>>

    @GET(NetworkConfig.PROFILES)
    fun getCharacterDetail(
        @Path(NetworkConfig.SEARCH_PATH) characterName: String
    ): Call<SearchedCharacterDetail>

    @GET(NetworkConfig.EQUIPMENT)
    fun getCharacterEquipment(
        @Path(NetworkConfig.SEARCH_PATH) characterName: String
    ): Call<List<Equipment>>

    @GET(NetworkConfig.GEMS)
    fun getCharacterGem(
        @Path(NetworkConfig.SEARCH_PATH) characterName: String
    ): Call<GemAndEffect>

    @GET(NetworkConfig.CARDS)
    fun getCharacterCard(
        @Path(NetworkConfig.SEARCH_PATH) characterName: String
    ): Call<CardsWithEffects>

    @GET(NetworkConfig.COMBAT_SKILLS)
    fun getCharacterSkills(
        @Path(NetworkConfig.SEARCH_PATH) characterName: String
    ): Call<List<CombatSkills>>

    @GET(NetworkConfig.ENGRAVINGS)
    fun getCharacterEngravings(
        @Path(NetworkConfig.SEARCH_PATH) characterName: String
    ): Call<SkillEngravingsAndEffects>

    @GET(NetworkConfig.ARK_PASSIVE)
    fun getCharacterArkPassive(
        @Path(NetworkConfig.SEARCH_PATH) characterName: String
    ): Call<ArkPassive>
}
