package com.hongmyeoun.goldcalc.model.searchedInfo.card

import com.google.gson.annotations.SerializedName

data class CardsWithEffects(
    @SerializedName("Cards") val cards: List<Cards>,
    @SerializedName("Effects") val effects: List<CardEffects>
)

data class Cards(
    @SerializedName("Slot") val slot: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Icon") val icon: String,
    @SerializedName("AwakeCount") val awakeCount: Int,
    @SerializedName("AwakeTotal") val awakeTotal: Int,
    @SerializedName("Grade") val grade: String,
    @SerializedName("Tooltip") val tooltip: String,
)

data class CardEffects(
    @SerializedName("Index") val index: Int,
    @SerializedName("Items") val items: List<CardSetOption>
)

data class CardSetOption(
    @SerializedName("Name") val name: String,
    @SerializedName("Description") val description: String,
)