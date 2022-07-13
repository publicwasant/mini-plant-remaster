package com.publicwasant.data_manager.entities

import java.io.Serializable
import java.text.NumberFormat
import java.util.*

data class Price(
    val original: Double,
    val actual: Double,
    val discount: Discount
): Serializable {
    fun originalPlainText(): String = NumberFormat.getCurrencyInstance(Locale("TH", "th")).format(original)
    fun actualPlainText(): String = NumberFormat.getCurrencyInstance(Locale("TH", "th")).format(actual)
}