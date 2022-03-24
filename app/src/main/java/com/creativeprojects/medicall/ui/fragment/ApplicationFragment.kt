package com.creativeprojects.medicall.ui.fragment

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentApplicationBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ApplicationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentApplicationBinding
    private lateinit var googleMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicationBinding.inflate(inflater, container, false)
        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        binding.fabNotifications.setOnClickListener(View.OnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_application_to_notificationFragment)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }
}