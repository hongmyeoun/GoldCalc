package com.hongmyeoun.goldcalc.model.roomDB

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
}
