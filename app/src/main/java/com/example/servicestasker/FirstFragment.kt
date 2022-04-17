package com.example.servicestasker

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Context.JOB_SCHEDULER_SERVICE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.servicestasker.databinding.FragmentFirstBinding
import com.example.servicestasker.service.MyService


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class FirstFragment : Fragment() {

    private val TAG = "FirstFragment"
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val jobId = 101
    private lateinit var jobScheduler: JobScheduler


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobScheduler = activity?.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val serviceIntent = Intent(requireContext(), MyService::class.java)


        binding.apply {


            btnStartService.setOnClickListener {
                startJob()
            }

            btnStopService.setOnClickListener {
                stopJob()
            }

        }

    }

    private fun startJob() {
        val componentName = ComponentName(requireContext(), MyService::class.java)
        val jobInfo = JobInfo.Builder(jobId, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
            .setPeriodic(15 * 60 * 1000)
            .setRequiresCharging(false)
            .setPersisted(true) // job will still work after the device rebooted (restart)
            .build()

        if (jobScheduler.schedule(jobInfo) == JobScheduler.RESULT_SUCCESS)
            Log.d(TAG, "startJob: ${Thread.currentThread().id}  job Success")
        else
            Log.d(TAG, "startJob: ${Thread.currentThread().id}  job Failed")

    }

    private fun stopJob() {
        jobScheduler.cancel(jobId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}