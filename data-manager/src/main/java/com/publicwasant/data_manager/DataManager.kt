package com.publicwasant.data_manager

import android.content.Context
import android.os.Build
import android.util.Log
import com.auth0.android.jwt.JWT
import java.util.*

class DataManager(context: Context) {
//    init {
//        val token: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdGF0dXMiOjAsImlkIjo4LCJpYXQiOiI0czdpejBvLmFjY2VwdCJ9.abBNEbWEfqQcb0IEOVZe8_7pNfdIfrOF63wGGlzn3Ro"
//        val decode = decodeToken(token)
//
//        Log.d("token", decode)
//    }
//
//    private fun decodeToken(jwt: String): String {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return "Requires SDK 26"
//        val parts = jwt.split(".")
//        return try {
//            val charset = charset("UTF-8")
//            val header = String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
//            val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
//            "$header"
//            "$payload"
//        } catch (e: Exception) {
//            "Error parsing JWT: $e"
//        }
//    }

    val employeeManager: EmployeeManager = EmployeeManager(context)
    val productManager: ProductManager = ProductManager(context)
}