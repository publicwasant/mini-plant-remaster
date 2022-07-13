package com.publicwasant.data_manager.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="employees")
data class Employee(
    @PrimaryKey
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val addr: String,
    val phone: String,
    val status: Int,
    val image: String
)