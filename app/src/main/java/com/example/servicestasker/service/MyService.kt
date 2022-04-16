package com.example.servicestasker.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import kotlin.random.Random

const val TAG = "*class : MyService "

class MyService: Service() {

    private var isGenerationActive =false
    private var numberLimit = 1  // to break the loop when reach some condition ( numberLimit > 100 )
    var randomNumber = 0

    // coroutine job
    private val job = Job()
    private val coroutineScope = CoroutineScope(job) // custom coroutine
    // Binder given to clients
    private val binder = MyServiceBinder()

    override fun onBind(p0: Intent?): IBinder {
     return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d(TAG, "onStartCommand: Start")

        isGenerationActive=true

        coroutineScope.launch(Dispatchers.IO){
            yield()
            randomNumberGeneration()   // why don't cancel even i all job cancel
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopRandomNumberGeneration()
        Log.d(TAG, "onDestroy: Service Stopped ")
        super.onDestroy()

    }

    private fun randomNumberGeneration()
    {
        while (isGenerationActive){
            randomNumber = (1 .. 100).random()
            Thread.sleep(1000)
            Log.d(TAG, "current number $randomNumber | current Thread ${Thread.currentThread()}")
            numberLimit++

            if (numberLimit>50)
                break
        }
    }
    
    private fun stopRandomNumberGeneration(){
        isGenerationActive=false
        job.cancel()
        Log.d(TAG, "stopRandomNumberGeneration: has stop")
    }


    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class MyServiceBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): MyService = this@MyService
    }
}