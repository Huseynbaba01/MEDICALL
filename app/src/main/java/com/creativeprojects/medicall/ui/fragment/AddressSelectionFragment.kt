package com.creativeprojects.medicall.ui.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.creativeprojects.medicall.databinding.FragmentAddressSelectionBinding
import com.creativeprojects.medicall.event.HistoryItemDeletedEvent
import com.creativeprojects.medicall.event.HistoryItemSelectedEvent
import com.creativeprojects.medicall.model.AddressHistoryItem
import com.creativeprojects.medicall.ui.adapter.AddressHistoryAdapter
import com.creativeprojects.medicall.utils.helper.CommonHelper
import com.creativeprojects.medicall.utils.helper.MapHelper
import com.creativeprojects.medicall.utils.helper.MessageHelper
import com.creativeprojects.medicall.utils.helper.TimeHelper
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
import org.greenrobot.eventbus.ThreadMode


class AddressSelectionFragment : BaseFragment(), OnMapReadyCallback,
    OnFocusChangeListener,
    GoogleMap.OnMapLongClickListener {

    private val TAG: String = "AddressSelection"

    private var addressHistoryAdapter: AddressHistoryAdapter = AddressHistoryAdapter(emptyList())
    private var historyItems : List<AddressHistoryItem> = ArrayList()
    private var currentLatLng: LatLng? = null

    private lateinit var addressSelectionViewModel : AddressSelectionViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentAddressSelectionBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var selectedLocation : LatLng


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

        binding.historyItems.adapter = addressHistoryAdapter//TODO replace with the appropriate history list:)
        binding.historyItems.layoutManager = LinearLayoutManager(context)
        binding.searchInput.onFocusChangeListener = this
        binding.searchButton.setOnClickListener {
            binding.searchInput.clearFocus()
        }

        binding.btnAssign.setOnClickListener {
            findNavController().navigate(AddressSelectionFragmentDirections.actionAddressSelectionFragmentToContinueWithPhoneNumberFragment())
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
                    selectLocation(LatLng(location.latitude, location.longitude))
                    addToHistoryItems()
                }
            }
        }

    }

    override fun onMapReady(map: GoogleMap) {
        Log.d(TAG, "onMapReady: Map is ready")
        googleMap = map
        googleMap.setOnMapLongClickListener(this)
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (hasFocus)
            binding.historyItemsCard.visibility = VISIBLE
        else if (!hasFocus)
            binding.historyItemsCard.visibility = GONE
    }



    private fun addToHistoryItems(){
        val handler = Handler(Looper.getMainLooper())
        MessageHelper.showProgressDialog(requireContext(), "Getting location info\n\nPlease wait...")

        handler.postDelayed({
            Log.d("MyTagHere", "addToHistoryItems: 158")
            val address = MapHelper.getLocationInfo(requireContext(), selectedLocation, 10)
            val title = address.subThoroughfare + " - " + address.thoroughfare
            val subtitle = "${address.locality}, ${address.countryName}"
            Log.d("MyTagHere", "addToHistoryItems: 162")
            MessageHelper.closeProgressDialog()
            Log.d("MyTagHere", "addToHistoryItems: 164")

            addressSelectionViewModel.insertHistoryItem(
                AddressHistoryItem(
                    TimeHelper.getCurrentTimeInMillis(),
                    selectedLocation.latitude,
                    selectedLocation.longitude,
                    title,
                    subtitle
                )
            )
        },500)
}


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun  onHistoryItemSelectedEvent(event : HistoryItemSelectedEvent){
        CommonHelper.closeKeyboard(binding.searchInput)
        binding.historyItemsCard.visibility = GONE
        binding.searchInput.clearFocus()
        selectLocation(LatLng(event.historyItem.latitude, event.historyItem.longitude))
        addToHistoryItems()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.ASYNC)
    fun  onHistoryItemDeletedEvent(event : HistoryItemDeletedEvent){
        addressSelectionViewModel.deleteHistoryItem(event.historyItem)
    }

    private fun selectLocation(latLng: LatLng){
        selectedLocation = latLng
        MapHelper.clearAllMarkers(googleMap)
        MapHelper.addMarkerTo(selectedLocation, googleMap, requireContext())
        requireActivity().runOnUiThread {
            MapHelper.animateCameraTo(selectedLocation, googleMap, 1000)
        }
    }

    override fun onMapLongClick(latlng: LatLng) {
        selectLocation(latlng)
        addToHistoryItems()
    }
}