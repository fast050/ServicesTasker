package com.example.servicestasker.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import kotlin.random.Random

const val TAG = "*class : MyService "

class MyService: Service() {

    private var isGenerationActive =false
    private val job = Job()
    var numberLimit = 1  // to break the loop when reach some condition ( numberLimit > 100 )
    private val coroutineScope = CoroutineScope(job) // custom coroutine

    override fun onBind(p0: Intent?): IBinder? {
     return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d(TAG, "onStartCommand: Start")

        isGenerationActive=true

        coroutineScope.launch {
            randomNumberGeneration()   // why don't cancel even i all job cancel
            delay(0)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRandomNumberGeneration()
        Log.d(TAG, "onDestroy: Service Stopped ")
    }

    private fun randomNumberGeneration()
    {
        while (isGenerationActive){
            val randomNumber = (1 .. 100).random()
            Thread.sleep(1000)
            Log.d(TAG, "startRandomNumberGeneration: current number $randomNumber")
            numberLimit++

            if (numberLimit>50)
                break
        }
    }
    
    private fun stopRandomNumberGeneration(){
       // isGenerationActive=false
        job.cancel()
        Log.d(TAG, "stopRandomNumberGeneration: has stop")
    }
}