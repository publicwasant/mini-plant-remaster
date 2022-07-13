package com.publicwasant.data_manager.service

import android.annotation.SuppressLint
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Service {
    companion object {
        val TAG: String = "Service"

        @SuppressLint("StaticFieldLeak")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://mini-plant.comsciproject.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val instance: Service = Service()
    }
}