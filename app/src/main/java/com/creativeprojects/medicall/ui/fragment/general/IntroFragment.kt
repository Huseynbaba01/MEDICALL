package com.creativeprojects.medicall.ui.fragment.general

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.creativeprojects.medicall.databinding.FragmentIntroBinding
import com.creativeprojects.medicall.utils.helper.CommonHelper
import com.creativeprojects.medicall.utils.mock.DoAsyncTask
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.internal.service.Common
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.grpc.InternalChannelz.id


class IntroFragment : Fragment() {
    private lateinit var binding: FragmentIntroBinding
    private var isGPSOpen = false
    private val TAG = "MyTagHere"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(inflater, container, false)

        checkConnection()
        Log.d(TAG, "onCreateView: passed checkConnection")






        return binding.root
    }

    private fun checkConnection() {
        Log.d(TAG, "checkConnection: we are here!")
        val fireStore = Firebase.firestore
        fireStore.collection("accepted")
            .whereEqualTo("accepted",true)
            .get().addOnSuccessListener {
                Log.d(TAG, "checkConnection: ${CommonHelper.isNetworkAvailable(requireContext())},${it.isEmpty}")
                if(CommonHelper.isNetworkAvailable(requireContext()) && !it.isEmpty){
                    continueActions()
                }else{
                    setError()
                }
            }

    }

    private fun setError() {
        findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToConnectionError())
//        Navigation.findNavController(this).navigate(IntroFragmentDirections.actionIntroFragmentToConnectionError())
    }

    private fun continueActions() {
        checkGPSEnabled()
        checkPermissions()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                try {
                    findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToSelectUserTypeFragment())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, 2000
        )
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }
    }

    private fun turnOnGPS() {
        val request = LocationRequest.create().apply {
            interval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(request)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(requireActivity(), 12345)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }.addOnSuccessListener {
            //here GPS is On
        }
    }

    private fun checkGPSEnabled() {
        val manager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER).not()) {
            turnOnGPS()
        }
    }



}