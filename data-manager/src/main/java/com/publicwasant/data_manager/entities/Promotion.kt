package com.publicwasant.data_manager.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="promotions")
data class Promotion (
    @PrimaryKey
    val id: Int,
    val start: String,
    val end: String,
    val discount: Double,
    val name: String,
    val detail: String,
    val images: List<String>
): Serializable {
    fun discountPercentPlainText(): String = "-$discount%"
    fun startFormatted(): String = start
    fun endFormatted(): String = end
}