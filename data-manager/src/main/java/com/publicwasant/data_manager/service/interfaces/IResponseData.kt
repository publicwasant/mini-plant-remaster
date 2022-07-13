package com.publicwasant.data_manager.service.interfaces

interface IResponseData<T> {
    fun isError(): Boolean
    fun isNotError(): Boolean
}