package com.publicwasant.network_manager

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

class TransportStatus(var context: Context) {
    companion object {
        val TAG: String = "Network"

        fun isOnline(context: Context): Boolean {
            return try {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val netInfo = connectivityManager.activeNetworkInfo

                ((netInfo != null) && netInfo.isConnected)
            } catch (e: NullPointerException) {
                false
            }
        }
    }

    private var onConnectedCallback: (() -> Unit)? = null
    private var onDisconnectedCallback: (() -> Unit)? = null

    private fun connectedNotify() = Handler(Looper.getMainLooper()).post {
        Log.i(TAG, "Connected")
        onConnectedCallback?.invoke()
    }

    private fun disconnectedNotify() = Handler(Looper.getMainLooper()).post {
        Toast.makeText(context, "เชื่อมต่อล้มเหลว", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Disconnected")
        onDisconnectedCallback?.invoke()
    }

    fun notifies() {
        when (isOnline(context)) {
            true -> connectedNotify()
            false -> disconnectedNotify()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onConnect(callback: () -> Unit): TransportStatus {
        onConnectedCallback = callback
        return this
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onDisconnect(callback: () -> Unit): TransportStatus {
        onDisconnectedCallback = callback
        return this
    }
}