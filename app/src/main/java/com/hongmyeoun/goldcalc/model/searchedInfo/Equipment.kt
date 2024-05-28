package com.hongmyeoun.goldcalc.model.searchedInfo

import com.google.gson.annotations.SerializedName

data class Equipment(
  @SerializedName("Type") val type: String,
  @SerializedName("Name") val name: String,
  @SerializedName("Icon") val icon: String,
  @SerializedName("Grade") val grade: String,
  @SerializedName("Tooltip") val tooltip: String,
)

data class CharacterEquipment(
  val type: String,
  val grade: String,
  val upgradeLevel: String,
  val itemLevel: String,
  val itemQuality: Int,
  val itemIcon: String,
  val elixirFirst: String,
  val elixirSecond: String,
  val transcendenceLevel: String,
  val transcendenceTotal: String,
  val highUpgradeLevel: String,
)