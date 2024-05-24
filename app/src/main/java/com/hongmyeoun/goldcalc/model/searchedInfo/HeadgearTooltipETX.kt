package com.hongmyeoun.goldcalc.model.searchedInfo

import com.google.gson.annotations.SerializedName

// 투구 E(엘릭서 O), T(초월 O), X(만렙 X)
data class HeadgearTooltipETX(
    @SerializedName("Element_000") val element000: BasicElement? = null,
    @SerializedName("Element_001") val element001: ItemTitle? = null,
    @SerializedName("Element_002") val element002: BasicElement? = null,
    @SerializedName("Element_003") val element003: BasicElement? = null,
    @SerializedName("Element_004") val element004: BasicElement? = null,
    @SerializedName("Element_005") val element005: BasicElement? = null,
    @SerializedName("Element_006") val element006: ItemPartBox? = null,
    @SerializedName("Element_007") val element007: ItemPartBox? = null,
    @SerializedName("Element_008") val element008: Progress? = null,
    @SerializedName("Element_009") val element009: Transendence? = null,
    @SerializedName("Element_010") val element010: ElixirLevelAndInfo? = null,
    @SerializedName("Element_011") val element011: ElixirLevelAndInfo? = null,
    @SerializedName("Element_012") val element012: ItemPartBox? = null,
    @SerializedName("Element_013") val element013: SetItemGroup? = null,
    @SerializedName("Element_014") val element014: SetOptionInfo? = null,
    @SerializedName("Element_015") val element015: BasicElement? = null,
    @SerializedName("Element_016") val element016: BasicElement? = null,
    @SerializedName("Element_017") val element017: BasicElement? = null,
)

data class BasicElement(
    val type: String,
    val value: String
)

data class ItemTitle(
    val type: String,
    val value: BasicItemTitle
)

data class BasicItemTitle(
    val bEquip: Int,
    val leftStr0: String,
    val leftStr1: String,
    val leftStr2: String,
    val qualityValue: Int,
    val rightStr0: String,
    val slotData: ItemSlotData
)

data class ItemSlotData(
    val advBookIcon: Int,
    val battleItemTypeIcon: Int,
    val cardIcon: Boolean,
    val friendship: Int,
    val iconGrade: Int,
    val iconPath: String,
    val imagePath: String,
    val islandIcon: Int,
    val petBorder: Int,
    val rtString: String,
    val seal: Boolean,
    val temporary: Int,
    val town: Int,
    val trash: Int
)

data class ItemPartBox(
    val type: String,
    val value: ItemPart
)

data class ItemPart(
    @SerializedName("Element_000") val element000: String,
    @SerializedName("Element_001") val element001: String
)

data class Progress(
    val type: String,
    val value: ProgressValue
)

data class ProgressValue(
    val forceValue: String,
    val maximum: Int,
    val minimum: Int,
    val title: String,
    val value: Int,
    val valueType: Int
)

data class Transendence(
    val type: String,
    val value: Tr
)

data class Tr(
    @SerializedName("Element_000") val element000: TrContent
)

data class TrContent(
    val contentStr: TrInfo,
    val topStr: String
)

data class TrInfo(
    @SerializedName("Element_000") val element000: Information,
    @SerializedName("Element_001") val element001: Information,
    @SerializedName("Element_002") val element002: Information,
    @SerializedName("Element_003") val element003: Information,
    @SerializedName("Element_004") val element004: Information,
    @SerializedName("Element_005") val element005: Information,
)

data class Information(
    val bPoint: Boolean,
    val contentStr: String
)

data class ElixirLevelAndInfo(
    val type: String,
    val value: Elixir
)

data class Elixir(
    @SerializedName("Element_000") val element000: ElixirContent
)

data class ElixirContent(
    val contentStr: ElixirInfo,
    val topStr: String
)

data class ElixirInfo(
    @SerializedName("Element_000") val element000: Information,
    @SerializedName("Element_001") val element001: Information
)

data class SetItemGroup(
    val type: String,
    val value: SetItemGroupValue
)

data class SetItemGroupValue(
    val firstMsg: String,
    val itemData: ItemData
)

data class ItemData(
    @SerializedName("Element_000") val element000: ItemDataInfo,
    @SerializedName("Element_001") val element001: ItemDataInfo,
    @SerializedName("Element_002") val element002: ItemDataInfo,
    @SerializedName("Element_003") val element003: ItemDataInfo,
    @SerializedName("Element_004") val element004: ItemDataInfo,
    @SerializedName("Element_005") val element005: ItemDataInfo
)

data class ItemDataInfo(
    val label: String,
    val slotData: SetOptionSlotData
)

data class SetOptionSlotData(
    val iconGrade: Int,
    val iconPath: String,
    val imagePath: String
)

data class SetOptionInfo(
    val type: String,
    val value: SetOptionInfoValue
)

data class SetOptionInfoValue(
    @SerializedName("Element_000") val element000: SetInfoStr,
    @SerializedName("Element_001") val element001: SetInfoStr,
    @SerializedName("Element_002") val element002: SetInfoStr,
)

data class SetInfoStr(
    val contentStr: SetInfo,
    val topStr: String
)

data class SetInfo(
    @SerializedName("Element_000") val element000: Information
)