package com.hongmyeoun.goldcalc.model.lostArkApi

import com.google.gson.annotations.SerializedName

data class SearchedCharacter(
    @SerializedName("ServerName") val serverName: String,
    @SerializedName("CharacterName") val characterName: String,
    @SerializedName("CharacterLevel") val characterLevel: Int,
    @SerializedName("CharacterClassName") val characterClassName: String,
    @SerializedName("ItemAvgLevel") val itemAvgLevel: String,
//    @SerializedName("ItemMaxLevel") val itemMaxLevel: String,
)

data class SearchedCharacterDetail(
    @SerializedName("CharacterImage") val characterImage: String,
    @SerializedName("ExpeditionLevel") val expeditionLevel: Int,
    @SerializedName("PvpGradeName") val pvpGradeName: String,
    @SerializedName("TownLevel") val townLevel: Int,
    @SerializedName("TownName") val townName: String,
    @SerializedName("Title") val title: String,
    @SerializedName("GuildMemberGrade") val guildMemberGrade: String,
    @SerializedName("GuildName") val guildName: String,
    @SerializedName("UsingSkillPoint") val usingSkillPoint: Int,
    @SerializedName("TotalSkillPoint") val totalSkillPoint: Int,
    @SerializedName("Stats") val stats: List<Stats>,
    @SerializedName("Tendencies") val tendencies: List<Tendency>,
    @SerializedName("ServerName") var serverName: String,
    @SerializedName("CharacterName") val characterName: String,
    @SerializedName("CharacterLevel") val characterLevel: Int,
    @SerializedName("CharacterClassName") val characterClassName: String,
    @SerializedName("ItemAvgLevel") val itemAvgLevel: String,
//    @SerializedName("ItemMaxLevel") val itemMaxLevel: String,
//    @SerializedName("ArkPassive") val arkPassive: ArkPassive
)

data class Stats(
    @SerializedName("Type") val type: String,
    @SerializedName("Value") val value: String,
    @SerializedName("Tooltip") val tooltip: List<String>
)

data class Tendency(
    @SerializedName("Type") val type: String,
    @SerializedName("Point") val point: Int,
    @SerializedName("MaxPoint") val maxPoint: Int
)

