package com.publicwasant.data_manager.local_db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.publicwasant.data_manager.entities.Price

class PriceTypeConverter {
    @TypeConverter
    fun fromPriceStringToPrice(value: String?): Price = Gson().fromJson(value, object : TypeToken<Price>(){}.type)

    @TypeConverter
    fun fromPriceToString(price: Price?): String = Gson().toJson(price)
}