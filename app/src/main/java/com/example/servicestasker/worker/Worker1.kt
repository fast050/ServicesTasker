package com.example.servicestasker.worker

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class Worker1(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    private val TAG = "Worker1"
    private var isRandomGeneratorActive = false
    override fun doWork(): Result {
        isRandomGeneratorActive =true
        Log.d(TAG, "doWork: work is Active")
        // Do the work here--in this case, upload the images.
        startRandomNumber()
        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(TAG, "onStopped: work has stopped")
        isRandomGeneratorActive =false
    }
    private fun startRandomNumber()
    {
        var counter = 1
        while (counter<=5 && isRandomGeneratorActive){
            val randomNumber  = (1..100).random()
            Log.d(TAG, "startRandomNumber: $randomNumber")
            counter++
            Thread.sleep(1000)
        }
    }
}
