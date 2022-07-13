package com.publicwasant.data_manager.local_db

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.*
import com.publicwasant.data_manager.entities.Employee
import com.publicwasant.data_manager.local_db.dao.IProductDao
import com.publicwasant.data_manager.local_db.converter.DiscountTypeConverter
import com.publicwasant.data_manager.local_db.converter.PriceTypeConverter
import com.publicwasant.data_manager.local_db.converter.PromotionTypeConverter
import com.publicwasant.data_manager.local_db.converter.StringTypeConverter
import com.publicwasant.data_manager.entities.Product
import com.publicwasant.data_manager.local_db.dao.IEmployeeDao

@Database(
    entities=[
        Product::class,
        Employee::class,
    ],
    version=1,
    exportSchema=false
)

@TypeConverters(
    DiscountTypeConverter::class,
    PriceTypeConverter::class,
    PromotionTypeConverter::class,
    StringTypeConverter::class
)

abstract class AppDatabase: RoomDatabase() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = instance
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val ins = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_database"
                ).build()
                instance = ins
                return ins
            }
        }
    }

    abstract fun employee(): IEmployeeDao
    abstract fun product(): IProductDao
}