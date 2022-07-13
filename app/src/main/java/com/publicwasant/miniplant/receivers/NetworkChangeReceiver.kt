package com.publicwasant.miniplant.receivers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.publicwasant.network_manager.NetworkManager

class NetworkChangeReceiver: BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        NetworkManager.cast(context!!) { net ->
            net.notifies()
        }
    }
}