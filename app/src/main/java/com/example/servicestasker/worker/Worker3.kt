package com.example.servicestasker.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


class Worker3(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams)
{

    private val TAG = "Worker3"


    override fun doWork(): Result {

        // Do the work here--in this case, upload the images.
        startRandomNumber()

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }


    private fun startRandomNumber()
    {
        var counter = 1
        while (counter<=5){
            val randomNumber  = (1..100).random()
            Log.d(TAG, "startRandomNumber: $randomNumber")
            counter++
            Thread.sleep(1000)
        }
    }
}