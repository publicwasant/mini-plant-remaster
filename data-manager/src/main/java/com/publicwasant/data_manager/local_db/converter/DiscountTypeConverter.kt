package com.publicwasant.data_manager.local_db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.publicwasant.data_manager.entities.Discount

class DiscountTypeConverter {
    @TypeConverter
    fun fromDiscountStringToDiscount(value: String?): Discount = Gson().fromJson(value, object : TypeToken<Discount>(){}.type)

    @TypeConverter
    fun fromDiscountToString(discount: Discount?): String = Gson().toJson(discount)
}