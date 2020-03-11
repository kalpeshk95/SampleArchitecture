package com.architecture.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.architecture.utils.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
//            val data = intent.getStringExtra("DATA")
//            Log.i( "Received data : $data")

            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable && activeNetworkInfo.isConnected) {
                Toast.makeText(context, "Online", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("onReceive : ${e.message}")
        }
    }
}