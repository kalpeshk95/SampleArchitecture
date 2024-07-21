package com.architecture.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import timber.log.Timber

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetwork
            if (activeNetworkInfo != null /*&& activeNetworkInfo.isAvailable && activeNetworkInfo.isConnected*/) {
                Toast.makeText(context, "Online", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Timber.e("onReceive : ${e.message}")
        }
    }
}