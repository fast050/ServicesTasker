package com.example.servicestasker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.servicestasker.databinding.FragmentFirstBinding
import com.example.servicestasker.worker.Worker1
import com.example.servicestasker.worker.Worker2
import com.example.servicestasker.worker.Worker3
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var workManager: WorkManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workManager= WorkManager.getInstance(requireContext())

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }


        binding.apply {



            btnStartService.setOnClickListener{
                val workRequest1 = OneTimeWorkRequestBuilder<Worker1>()
                    .addTag(TAG_WORKER1).build()
                val workRequest2 = OneTimeWorkRequestBuilder<Worker2>()
                    .addTag(TAG_WORKER2).build()
                val workRequest3= OneTimeWorkRequestBuilder<Worker3>()
                    .addTag(TAG_WORKER3).build()
                workManager.beginWith(arrayListOf(workRequest1,workRequest2)).then(workRequest3).enqueue()
            }

            btnStopService.setOnClickListener {
                workManager.cancelAllWorkByTag(TAG_WORKER1)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val TAG_WORKER1= "tagWorker1"
        const val TAG_WORKER2= "tagWorker2"
        const val TAG_WORKER3= "tagWorker3"

    }
}