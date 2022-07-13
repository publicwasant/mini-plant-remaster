package com.publicwasant.data_manager.service.interfaces

import androidx.lifecycle.MutableLiveData
import com.publicwasant.data_manager.service.implement.RequestDataImp
import io.reactivex.Observable

interface IRequestData<T : Any> {
    fun onReceive(observable: Observable<T>): MutableLiveData<T>
    fun onError(e: Throwable): RequestDataImp<T>
}