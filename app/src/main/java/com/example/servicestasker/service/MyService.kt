package com.example.servicestasker.service

import android.app.IntentService
import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import kotlin.random.Random

const val TAG = "*class : MyService "

class MyService() : JobService() {

    private var isGenerationActive =false
    private var numberLimit = 1  // to break the loop when reach some condition ( numberLimit > 100 )
    var randomNumber = 0
    private var counter =0

    // coroutine job
    private val job = Job()
    private val coroutineScope = CoroutineScope(job) // custom coroutine

    override fun onStartJob(p0: JobParameters?): Boolean {
        doBackGroundWork()
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob: called")
        return true
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        stopBackGroundWork()
        super.onDestroy()
    }

    private  fun doBackGroundWork()
    {
        Log.d(TAG, "onStartCommand: Start")

        isGenerationActive=true

        coroutineScope.launch(Dispatchers.IO){
            yield()
            randomNumberGeneration()   // why don't cancel even i all job cancel
        }
    }

    private fun randomNumberGeneration()
    {
        while (isGenerationActive){
            randomNumber = (1 .. 100).random()
            Thread.sleep(1000)
            counter++
            Log.d(TAG, "${counter} - current number $randomNumber | current Thread ${Thread.currentThread()}")
            numberLimit++
            if (numberLimit>200)
                break
        }
    }
    
    private fun stopBackGroundWork(){
        isGenerationActive=false
        job.cancel()
        Log.d(TAG, "stopRandomNumberGeneration: has stop")
    }

}