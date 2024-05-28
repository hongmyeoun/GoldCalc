package com.hongmyeoun.goldcalc.model.searchedInfo

import com.google.gson.annotations.SerializedName

data class Equipment(
  @SerializedName("Type") val type: String,
  @SerializedName("Name") val name: String,
  @SerializedName("Icon") val icon: String,
  @SerializedName("Grade") val grade: String,
  @SerializedName("Tooltip") val tooltip: String,
)