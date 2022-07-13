package com.publicwasant.data_manager.service.interfaces

import com.publicwasant.data_manager.service.implement.ResponseDataImp
import com.publicwasant.data_manager.entities.Product
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IProductData {
    @GET("product")
    fun get(): Observable<ResponseDataImp<Product>>

    @GET("product")
    fun getById(@Query("id") id: Int): Observable<ResponseDataImp<Product>>
}