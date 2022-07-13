package com.publicwasant.data_manager.local_db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.publicwasant.data_manager.entities.Product

@Dao
interface IProductDao {
    @Query("SELECT * FROM products")
    fun get(): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE id=:id")
    fun getById(id: Int): LiveData<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(products: List<Product>)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM products")
    fun delete()
}