package com.publicwasant.network_manager.implement

import android.content.Context
import android.util.Log
import com.publicwasant.network_manager.TransportStatus
import com.publicwasant.network_manager.interfaces.IConnectivityManager

open class ConnectivityManager(
    private val context: Context
): IConnectivityManager {
    companion object {
        val TAG: String = "ConnectivityManager"
        var onChangeListener: OnChangeListener? = null
    }

    interface OnChangeListener {
        fun onConnect()
        fun onDisconnect()
    }

    override fun isOnline(): Boolean {
        val online = try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo

            ((netInfo != null) && netInfo.isConnected)
        } catch (e: NullPointerException) {
            false
        }

        return when (online) {
            true -> {
                Log.i(TransportStatus.TAG, "Connected")
                true
            }
            false -> {
                Log.i(TransportStatus.TAG, "Disconnected")
                false
            }
        }
    }

    fun setOnChangeListener(onChangeListener: OnChangeListener) {
        ConnectivityManager.onChangeListener = onChangeListener
    }
}