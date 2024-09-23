package com.hongmyeoun.goldcalc.model.profile.arkpassive

import com.google.gson.annotations.SerializedName

data class ArkPassive(
    @SerializedName("IsArkPassive") val isArkPassive: Boolean,
    @SerializedName("Points") val points: List<ArkPassivePoint>,
    @SerializedName("Effects") val effects: List<ArkPassiveSkillEffects>,
)

data class ArkPassivePoint(
    @SerializedName("Name") val name: String,
    @SerializedName("Value") val value: Int,
    @SerializedName("Tooltip") val tooltip: String
)

data class ArkPassiveSkillEffects(
    @SerializedName("Name") val name: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Icon") val icon: String,
    @SerializedName("ToolTip") val tooltip: String
)

data class ArkPassiveNode(
    val type: String,
    val tier: String,
    val name: String,
    val level: String,
    val icon: String,
    val script: String,
)