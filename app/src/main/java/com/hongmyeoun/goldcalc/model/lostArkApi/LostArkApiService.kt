package com.hongmyeoun.goldcalc.model.lostArkApi

import com.hongmyeoun.goldcalc.model.searchedInfo.card.CardsWithEffects
import com.hongmyeoun.goldcalc.model.searchedInfo.equipment.Equipment
import com.hongmyeoun.goldcalc.model.searchedInfo.gem.GemAndEffect
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LostArkApiService {
    @GET("/characters/{characterName}/siblings")
    fun getCharacters(
        @Path("characterName") characterName: String
    ): Call<List<CharacterInfo>>

    @GET("/armories/characters/{characterName}/profiles")
    fun getCharacterDetail(
        @Path("characterName") characterName: String
    ): Call<CharacterDetail>

    @GET("/armories/characters/{characterName}/equipment")
    fun getCharacterEquipment(
        @Path("characterName") characterName: String
    ): Call<List<Equipment>>

    @GET("/armories/characters/{characterName}/gems")
    fun getCharacterGem(
        @Path("characterName") characterName: String
    ): Call<GemAndEffect>

    @GET("/armories/characters/{characterName}/cards")
    fun getCharacterCard(
        @Path("characterName") characterName: String
    ): Call<CardsWithEffects>
}
