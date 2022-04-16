package com.example.servicestasker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.servicestasker.databinding.FragmentFirstBinding
import com.example.servicestasker.service.MyService
import com.example.servicestasker.service.MyService.MyServiceBinder


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var myService :MyService? = null
    private var isServiceBound:Boolean = false
    private lateinit var serviceConnection: ServiceConnection
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val serviceIntent = Intent(requireContext(),MyService::class.java)


        binding.apply {


            btnStartService.setOnClickListener{
              activity?.startService(serviceIntent)
            }

            btnStopService.setOnClickListener {
                activity?.stopService(serviceIntent)
            }

            btnBindService.setOnClickListener {

                if (myService == null){
                    serviceConnection = object : ServiceConnection{
                        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {

                            //to get reference from the service
                            val myServiceBinder = p1 as MyServiceBinder
                            myService = myServiceBinder.getService()
                            isServiceBound =true
                        }

                        override fun onServiceDisconnected(p0: ComponentName?) {
                           isServiceBound=false
                        }

                    }
                }

                // BIND_AUTO_CREATE : it will create service if is not created when it try to bind
                activity?.bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE)


            }

            btnUnbindService.setOnClickListener {
                 if (isServiceBound) {
                     activity?.unbindService(serviceConnection)
                     isServiceBound = false
                 }
            }

            btnGetRandomNumber.setOnClickListener {
                if (isServiceBound)
                    textviewFirst.text = myService?.randomNumber.toString()
                else
                    textviewFirst.text = "not bound"
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}