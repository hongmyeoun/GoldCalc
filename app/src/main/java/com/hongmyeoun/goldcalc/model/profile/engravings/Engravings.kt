package com.hongmyeoun.goldcalc.model.profile.engravings

import com.google.gson.annotations.SerializedName

data class SkillEngravingsAndEffects(
    @SerializedName("Engravings") val engravings: List<SkillEngraving?>?,
    @SerializedName("Effects") val effect: List<SkillEffect>?,
    @SerializedName("ArkPassiveEffects") val arkPassiveEffect: List<ArkPassiveEffects>?
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

data class ArkPassiveEffects(
    @SerializedName("AbilityStoneLevel") val abilityStoneLevel: Int?,
    @SerializedName("Grade") val grade: String,
    @SerializedName("Level") val level: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Description") val description: String,
)

data class SkillEngravings(
    val awakenEngravingsPoint: String? = null,
    val name: String,
    val level: String,
    val description: String,
    val abilityStoneLevel: Int? = null,
    val icon: String? = null,
    val grade: String? = null,
)