package com.hongmyeoun.goldcalc.retrofitest

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
}
