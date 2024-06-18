package com.hongmyeoun.goldcalc.model.searchedInfo.engravings

import com.google.gson.annotations.SerializedName

data class SkillEngravingsAndEffects(
    @SerializedName("Engravings") val engravings: List<SkillEngraving?>? = null,
    @SerializedName("Effects") val effect: List<SkillEffect>
)

data class SkillEngraving(
    @SerializedName("Slot") val slot: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Icon") val icon: String,
    @SerializedName("Tooltip") val tooltip: String,
)

data class SkillEffect(
    @SerializedName("Icon") val icon: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Description") val description: String,
)

data class SkillEngravings(
    val awakenEngravingsPoint: String? = null,
    val name: String,
    val icon: String,
    val level: String,
)