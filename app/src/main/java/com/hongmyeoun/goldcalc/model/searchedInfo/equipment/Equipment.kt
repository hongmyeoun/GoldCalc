package com.hongmyeoun.goldcalc.model.searchedInfo.equipment

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
  val setOption: String, // 세트옵션(ex: 갈망)
  val elixirSetOption: String // 엘릭서 세트옵
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
  val engraving1Lv: String,
  val engraving1Op: String,
  val engraving2Lv: String,
  val engraving2Op: String,
  val engraving3Lv: String,
  val engraving3Op: String,
) : CharacterItem

data class Bracelet(
  val type: String,
  val grade: String,
  val name: String,
  val itemIcon: String,
  val specialEffect: List<Pair<String, String>>,
  val stats: List<Pair<String, String>>
) : CharacterItem
