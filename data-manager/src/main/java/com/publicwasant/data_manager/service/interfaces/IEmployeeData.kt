package com.publicwasant.data_manager.service.interfaces

import com.publicwasant.data_manager.entities.Employee
import com.publicwasant.data_manager.service.implement.ResponseDataImp
import io.reactivex.Observable
import retrofit2.http.GET

interface IEmployeeData {
    @GET("user/employee")
    fun get(): Observable<ResponseDataImp<Employee>>
}