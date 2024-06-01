package com.hongmyeoun.goldcalc.model.searchedInfo.gem

import com.google.gson.annotations.SerializedName

data class GemAndEffect(
    @SerializedName("Gems") val gems: List<Gems>,
    @SerializedName("Effects") val effects: List<Effects>,
)

data class Gems(
    @SerializedName("Slot") val slot: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Icon") val icon: String,
    @SerializedName("Level") val level: Int,
    @SerializedName("Grade") val grade: String,
    @SerializedName("Tooltip") val tooltip: String,
)

data class Effects(
    @SerializedName("GemSlot") val gemSlot: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Icon") val icon: String,
    @SerializedName("Tooltip") val tooltip: String,
)


data class Gem(
    val type: String,
    val level: Int,
    val gemIcon: String,
    val skillIcon: String,
    val skill: String,
    val effect: String
)
