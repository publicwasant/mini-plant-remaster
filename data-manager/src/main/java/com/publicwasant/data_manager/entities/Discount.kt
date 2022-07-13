package com.publicwasant.data_manager.entities

import java.io.Serializable
import java.text.NumberFormat
import java.util.*

data class Discount (
    val percent: Double,
    val difference: Double,
): Serializable {
    fun isDiscount(): Boolean = percent != 0.0
    fun percentPlainText(): String = "-$percent%"
    fun differencePlainText(): String = NumberFormat.getCurrencyInstance(Locale("TH", "th")).format(difference)
}