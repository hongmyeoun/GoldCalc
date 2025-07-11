package com.hongmyeoun.goldcalc.model.profile.equipment

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
  val elixirFirstLevel: String, // 엘릭서1 렙
  val elixirFirstOption: String, // 엘릭서1 옵션
  val elixirSecondLevel: String, // 엘릭서2 렙
  val elixirSecondOption: String, // 엘릭서2 옵션
  val transcendenceLevel: String, // 초월 단계
  val transcendenceTotal: String, // 초월 레벨
  val highUpgradeLevel: String, // 상급 재련 강화단계
  val elixirSetOption: String // 엘릭서 세트옵
) : CharacterItem

data class CharacterAccessory(
  val type: String,
  val grade: String,
  val name: String,
  val itemQuality: Int,
  val itemIcon: String,
  val grindEffect: String?,
  val arkPassivePoint: String?
) : CharacterItem

data class AbilityStone(
  val type: String,
  val grade: String,
  val name: String,
  val itemIcon: String,
  val hpBonus: String,
  val engraving1Lv: Int?,
  val engraving1Op: String,
  val engraving2Lv: Int?,
  val engraving2Op: String,
  val engraving3Lv: Int?,
  val engraving3Op: String,
) : CharacterItem

data class Bracelet(
  val type: String,
  val grade: String,
  val name: String,
  val itemIcon: String,
  val special: List<Pair<String, String>>,
  val basic: List<Pair<String, String>>,
  val combat: List<Pair<String, String>>
) : CharacterItem
