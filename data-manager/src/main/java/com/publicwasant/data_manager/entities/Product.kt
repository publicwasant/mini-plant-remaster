package com.publicwasant.data_manager.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="products")
data class Product (
    @PrimaryKey
    val id: Int,
    val name: String,
    val detail: String,
    val type: Int,
    val status: Int,
    val colors: List<String>,
    val size: List<String>,
    val images: List<String>,
    val promotions: List<Promotion>,
    val price: Price,
    var isFavorite: Boolean = false
): Serializable {
    fun typePlainText(): String = when (type) {
        1 -> "กระถางต้นไม้"
        2 -> "ต้นไม้"
        3 -> "หินตกแต่ง"
        4 -> "การ์ดอวยพร"
        5 -> "สินค้าสําเร็จรูป"
        else -> "ไม่ระบุประเภท"
    }

    fun colorsPlainText(): String = colors.toString()
        .replace("[", "")
        .replace("]", "")

    fun sizePlainText(): String = size.toString()
        .replace("[", "")
        .replace("]", "")
}



