package com.publicwasant.data_manager.local_db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringTypeConverter {
    @TypeConverter
    fun fromStringToList(value: String?): List<String> = Gson().fromJson(value, object : TypeToken<List<String>>(){}.type)

    @TypeConverter
    fun fromListToString(list: List<String?>): String = Gson().toJson(list)
}