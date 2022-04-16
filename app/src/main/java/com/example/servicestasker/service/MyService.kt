package com.example.servicestasker.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

const val TAG = "*class : MyService "

class MyService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
     return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d(TAG, "onStartCommand: Start")
       // stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onStartCommand: stop")

    }
}