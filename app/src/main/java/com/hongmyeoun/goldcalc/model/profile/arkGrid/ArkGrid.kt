package com.hongmyeoun.goldcalc.model.profile.arkGrid

import com.google.gson.annotations.SerializedName

data class ArkGrid(
    @SerializedName("Slots") val slots: List<ArkGridSlot>?,
    @SerializedName("Effects") val effects: List<ArkGridEffects>?
)

data class ArkGridSlot(
    @SerializedName("Index") val index: Int,
    @SerializedName("Icon") val icon: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Point") val point: Int,
    @SerializedName("Grade") val grade: String,
    @SerializedName("Tooltip") val tooltip: String,
    @SerializedName("Gems") val gems: List<ArkGridGem>?,
)

data class ArkGridGem(
    @SerializedName("Index") val index: Int,
    @SerializedName("Icon") val icon: String,
    @SerializedName("IsActive") val isActive: Boolean,
    @SerializedName("Grade") val grade: String,
    @SerializedName("Tooltip") val tooltip: String,
)

data class ArkGridEffects(
    @SerializedName("Name") val name: String,
    @SerializedName("Level") val level: Int,
    @SerializedName("Tooltip") val tooltip: String,
)

data class ArkGridCoreAndGemsTooltips(
    val coreOption: String,
    val coreTrigger: String,
    val gemBasicInfo: List<String>?,
    val gemPoint: List<String>?,
    val gemOption: List<String>?
)