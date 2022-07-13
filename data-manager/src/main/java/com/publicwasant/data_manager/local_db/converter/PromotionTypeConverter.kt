package com.publicwasant.data_manager.local_db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.publicwasant.data_manager.entities.Promotion

class PromotionTypeConverter {
    @TypeConverter
    fun fromPromotionStringToList(value: String?): List<Promotion> = Gson().fromJson(value, object : TypeToken<List<Promotion>>(){}.type)

    @TypeConverter
    fun fromPromotionListToString(list: List<Promotion?>): String = Gson().toJson(list)
}