package com.publicwasant.network_manager

import android.annotation.SuppressLint
import android.content.Context

class NetworkManager {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var transportStatusInstance: TransportStatus? = null

        fun getInstance(context: Context): TransportStatus {
            if (transportStatusInstance == null) {
                transportStatusInstance = TransportStatus(context)
            } else {
                transportStatusInstance?.context = context
            }

            return transportStatusInstance!!
        }

        fun cast(context: Context, callback: (TransportStatus) -> Unit) {
            callback.invoke(getInstance(context))
        }

        fun newCast(context: Context, callback: (TransportStatus) -> Unit) {
            callback.invoke(TransportStatus(context))
        }

        fun isOnline(context: Context): Boolean {
            return TransportStatus.isOnline(context)
        }
    }
}