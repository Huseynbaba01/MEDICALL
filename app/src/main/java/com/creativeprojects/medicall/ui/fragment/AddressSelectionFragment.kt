package com.creativeprojects.medicall.ui.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.creativeprojects.medicall.databinding.FragmentAddressSelectionBinding
import com.creativeprojects.medicall.event.HistoryItemDeletedEvent
import com.creativeprojects.medicall.model.AddressHistoryItem
import com.creativeprojects.medicall.ui.adapter.AddressHistoryAdapter
import com.creativeprojects.medicall.utils.helper.CommonHelper
import com.creativeprojects.medicall.utils.helper.MapHelper
import com.creativeprojects.medicall.utils.helper.TimeHelper
import com.creativeprojects.medicall.utils.mock.DoAsyncTask
import com.creativeprojects.medicall.utils.mock.MockCancellationToken
import com.creativeprojects.medicall.viewmodel.AddressSelectionViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import org.greenrobot.eventbus.Subscribe


class AddressSelectionFragment : BaseFragment(), OnMapReadyCallback,
    OnFocusChangeListener {

    private val TAG: String = "Address selection"

    private var addressHistoryAdapter: AddressHistoryAdapter = AddressHistoryAdapter(emptyList())
    private var historyItems : List<AddressHistoryItem> = ArrayList()
    private var currentLatLng: LatLng? = null

    private lateinit var addressSelectionViewModel : AddressSelectionViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentAddressSelectionBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = binding.mapFragment.getFragment()
        mapFragment.getMapAsync(this)
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1000
        )

        addressSelectionViewModel = ViewModelProvider(requireActivity())[AddressSelectionViewModel::class.java]

        addressSelectionViewModel.allHistoryItems.observe(
            viewLifecycleOwner
        ) {
            it.forEach {item ->
                Log.d(TAG, "onViewCreated86: "+item.latitude+" " + item.longitude+item.time+ " \n ")
            }
            historyItems =
                if(it.size >= 3) it.subList(0, 3)
                else             it
            addressHistoryAdapter.updateDataSet(historyItems)
        }


        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
//        getCurrentLocation()
        if (currentLatLng != null) {
            MapHelper.addMarkerTo(currentLatLng!!, googleMap, requireContext())
        }

        binding.historyItems.adapter = addressHistoryAdapter//TODO replace with the appropriate history list:)
        binding.historyItems.layoutManager = LinearLayoutManager(context)
        binding.searchInput.onFocusChangeListener = this
        binding.searchButton.setOnClickListener {
            binding.searchInput.clearFocus()
        }

        binding.btnAuto.setOnClickListener {
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
            } else {

                fusedLocationProviderClient.getCurrentLocation(
                    PRIORITY_HIGH_ACCURACY, MockCancellationToken()
                ).addOnSuccessListener { location ->
                    val latLng = LatLng(location.latitude, location.longitude)
                    Log.d(TAG, "onViewCreated: " + location.latitude + "   " + location.longitude)

                    MapHelper.addMarkerTo(latLng, googleMap, requireContext())
                    DoAsyncTask{
                        val address = MapHelper.getLocationInfo(requireContext(), latLng)
                        addToHistoryItems(
                                location,
                                address.subThoroughfare+" - "+address.thoroughfare,
                                "${address.locality}, ${address.countryName}"
                        ////TODO address should also be moved to the function arguments.
                        )
                    }.run()
                }
            }
        }

    }

    override fun onMapReady(p0: GoogleMap) {
        Log.d(TAG, "onMapReady: Map is ready")
        googleMap = p0
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (hasFocus)
            binding.historyItemsCard.visibility = VISIBLE
        else if (!hasFocus)
            binding.historyItemsCard.visibility = GONE
    }



    private fun addToHistoryItems(location: Location, title : String, subtitle : String){
        val historyItem = AddressHistoryItem(TimeHelper.getCurrentTimeInMillis(), location.latitude, location.longitude, title, subtitle)
        addressSelectionViewModel.insertHistoryItem(historyItem)
    }

    @Subscribe(sticky = true)
    public fun  onHistoryItemDeletedEvent(event : HistoryItemDeletedEvent){
        addressSelectionViewModel.deleteHistoryItem(event.historyItem)
    }
}