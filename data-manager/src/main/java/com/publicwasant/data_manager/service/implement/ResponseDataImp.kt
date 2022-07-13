package com.publicwasant.data_manager.service.implement

import android.util.Log
import com.publicwasant.data_manager.service.interfaces.IResponseData

class ResponseDataImp<T>(
    val status: Int,
    val descript: String,
    val error: String,
    val data: List<T>
): IResponseData<T> {
    override fun isError(): Boolean = status == 0
    override fun isNotError(): Boolean = status == 1

    override fun toString(): String {
        return "\n[RESPONSE]\n" +
                "\tstatus : ${isNotError()}\n" +
                "\tdescript : $descript\n" +
                "\tdata : [size=${data.size}]\n" +
                "\terror : $error"
    }
}