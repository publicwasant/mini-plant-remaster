package com.publicwasant.data_manager

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import com.publicwasant.data_manager.entities.Employee
import com.publicwasant.data_manager.service.Service
import com.publicwasant.data_manager.service.implement.RequestDataImp
import com.publicwasant.data_manager.service.implement.ResponseDataImp
import com.publicwasant.data_manager.service.interfaces.IEmployeeData
import com.publicwasant.network_manager.NetworkManager

class EmployeeManager(private val context: Context): RequestDataImp<ResponseDataImp<Employee>>() {
    private val serviceRepository: IEmployeeData = Service.retrofit.create(IEmployeeData::class.java)

    @RequiresApi(Build.VERSION_CODES.M)
    fun get(callback: (List<Employee>) -> Unit) {
        if (NetworkManager.isOnline(context)) {
            onReceive(serviceRepository.get()).observe(context as LifecycleOwner) { res ->
                if (res.isNotError()) {
                    Log.i(TAG, res.toString())
                    callback.invoke(res.data)
                } else {
                    Log.e(TAG, res.toString())
                }
            }
        }
    }
}