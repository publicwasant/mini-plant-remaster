package com.publicwasant.data_manager.service.implement

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.publicwasant.data_manager.service.interfaces.IRequestData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class RequestDataImp<T : Any>: IRequestData<T> {
    val TAG: String = "Service"

    override fun onError(e: Throwable): RequestDataImp<T> {
        Log.e(TAG, e.toString())
        return this
    }

    override fun onReceive(observable: Observable<T>): MutableLiveData<T> {
        val result: MutableLiveData<T> = MutableLiveData()

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    onError(e)
                }

                override fun onNext(products: T) {
                    result.postValue(products)
                }

                override fun onComplete() {
                }
            })

        return result
    }
}
