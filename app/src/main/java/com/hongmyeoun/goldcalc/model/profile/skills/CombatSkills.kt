package com.hongmyeoun.goldcalc.model.profile.skills

import com.google.gson.annotations.SerializedName

data class CombatSkills(
    @SerializedName("Name") val name: String,
    @SerializedName("Icon") val icon: String,
    @SerializedName("Level") val level: Int,
    @SerializedName("Type") val type: String,
    @SerializedName("IsAwakening") val isAwakening: Boolean,
    @SerializedName("Tripods") val tripods: List<Tripods>,
    @SerializedName("Rune") val rune: Rune?,
    @SerializedName("Tooltip") val tooltip: String,
)

data class Tripods(
    @SerializedName("Tier") val tier: Int,
    @SerializedName("Slot") val slot: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Icon") val icon: String,
    @SerializedName("IsSelected") val isSelected: Boolean,
    @SerializedName("Tooltip") val tooltip: String,
)

data class Rune(
    @SerializedName("Name") val name: String,
    @SerializedName("Icon") val icon: String,
    @SerializedName("Grade") val grade: String,
    @SerializedName("Tooltip") val tooltip: String,
)

data class Skills(
    val icon: String,
    val name: String,
    val level: Int,
    val tripods: List<Tripods>,
    val rune: Rune? = null,
    val runeTooltip: String? = null,
    val gem: Boolean,
)