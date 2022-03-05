package com.creativeprojects.medicall.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.creativeprojects.medicall.AddressHistoryItem
import com.creativeprojects.medicall.databinding.FragmentAddressSelectionBinding
import com.creativeprojects.medicall.helper.MapHelper
import com.creativeprojects.medicall.ui.adapter.AddressHistoryAdapter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class AddressSelectionFragment : Fragment(), OnMapReadyCallback, View.OnFocusChangeListener {
    private val TAG = "AddressSelection"
    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentAddressSelectionBinding
    private lateinit var mapFragment : SupportMapFragment


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAddressSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = binding.mapFragment.getFragment()
        mapFragment.getMapAsync(this)
        binding.historyItems.adapter = AddressHistoryAdapter(ArrayList())//TODO replace with the history list:)
        binding.historyItems.layoutManager = LinearLayoutManager(context)
        binding.searchInput.onFocusChangeListener = this

        binding.searchButton.setOnClickListener {
            binding.searchInput.clearFocus()
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        Log.d(TAG, "onMapReady: Map is ready")
        googleMap = p0
        val baku = LatLng(40.4093, 49.867092)
        MapHelper.addMarkerTo(baku, googleMap, requireContext())
        MapHelper.animateCameraTo(baku, googleMap, 3000)
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean){
        if(hasFocus && binding.historyItems.visibility == INVISIBLE)
            binding.historyItems.visibility = VISIBLE
        else if(!hasFocus && binding.historyItems.visibility == VISIBLE)
            binding.historyItems.visibility = INVISIBLE
    }

}