package com.hongmyeoun.goldcalc.model.searchedInfo

import com.google.gson.annotations.SerializedName

data class Equipment(
  @SerializedName("Type") val type: String,
  @SerializedName("Name") val name: String,
  @SerializedName("Icon") val icon: String,
  @SerializedName("Grade") val grade: String,
  @SerializedName("Tooltip") val tooltip: String,
)

interface CharacterItem

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
  val setOption: String,
) : CharacterItem

data class CharacterAccessory(
  val type: String,
  val grade: String,
  val name: String,
  val itemQuality: Int,
  val itemIcon: String,
  val combatStat1: String,
  val combatStat2: String,
  val engraving1: String,
  val engraving2: String,
  val engraving3: String,
) : CharacterItem

data class AbilityStone(
  val type: String,
  val grade: String,
  val name: String,
  val itemIcon: String,
  val hpBonus: String,
  val engraving1: String,
  val engraving2: String,
  val engraving3: String,
) : CharacterItem

data class Bracelet(
  val type: String,
  val grade: String,
  val name: String,
  val itemIcon: String,
  val effect: String,
) : CharacterItem