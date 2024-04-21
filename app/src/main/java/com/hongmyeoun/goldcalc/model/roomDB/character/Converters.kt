package com.hongmyeoun.goldcalc.model.roomDB.character

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromCheckList(checkList: CheckList): String {
        return Gson().toJson(checkList)
    }

    @TypeConverter
    fun toCheckList(checkListString: String): CheckList {
        val type = object : TypeToken<CheckList>() {}.type
        return Gson().fromJson(checkListString, type)
    }

    @TypeConverter
    fun fromRaidPhaseInfo(raidPhaseInfo: RaidPhaseInfo): String {
        return Gson().toJson(raidPhaseInfo)
    }

    @TypeConverter
    fun toRaidPhaseInfo(raidPhaseInfoString: String?): RaidPhaseInfo {
        if (raidPhaseInfoString.isNullOrEmpty()) {
            return RaidPhaseInfo() // 또는 원하는 기본값으로 초기화
        }
        val type = object : TypeToken<RaidPhaseInfo>() {}.type
        return Gson().fromJson(raidPhaseInfoString, type)
    }
}
