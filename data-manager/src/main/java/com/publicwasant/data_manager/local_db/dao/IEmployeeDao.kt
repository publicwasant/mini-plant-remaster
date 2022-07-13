package com.publicwasant.data_manager.local_db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.publicwasant.data_manager.entities.Employee

@Dao
interface IEmployeeDao {
    @Query("SELECT * FROM employees")
    fun get(): LiveData<List<Employee>>
}