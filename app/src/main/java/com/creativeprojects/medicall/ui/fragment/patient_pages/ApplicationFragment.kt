package com.creativeprojects.medicall.ui.fragment.patient_pages

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentApplicationBinding
import com.creativeprojects.medicall.utils.constant.Constants
import com.creativeprojects.medicall.utils.preferences.PreferenceHelper
import com.creativeprojects.medicall.utils.preferences.PreferenceHelper.get
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class ApplicationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentApplicationBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var mContext: Context
    private var isPatient = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicationBinding.inflate(inflater, container, false)
        mContext = requireContext()
        setClickListeners()
        isPatient = PreferenceHelper.getDefault(requireContext())[Constants.USER_TYPE_PREFERENCE]
        if(isPatient){
            binding.icnDoctor.setImageResource(R.drawable.ic_call_doctor)
            binding.txtDoctorName.text = "Əli Əliyev"
            binding.txtHospitalName.text = "1 saylı şəhər klinikası"
            binding.firstAidCard.visibility = VISIBLE
        }
        else{
            binding.icnDoctor.setImageResource(R.drawable.user_icon)
            binding.txtDoctorName.text = "Koronavirus"
            binding.txtHospitalName.text = "Neftçilər pr 125"
            binding.firstAidCard.visibility = GONE
        }
        return binding.root
    }

    private fun setClickListeners() {
        binding.fabNotifications.setOnClickListener(View.OnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_application_to_notificationFragment)
        })

        binding.btnCall.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:+994503432260")))
        }

        binding.btnMessage.setOnClickListener {
            openWhatsappContact("+994503432260")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        checkPermissions{
            googleMap.isMyLocationEnabled = true
        }
    }

    private fun checkPermissions(runnable: Runnable) {
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
            runnable.run()
        }
    }

    private fun openWhatsappContact(number: String){
        try {
            val uri = Uri.parse("smsto:$number")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.setPackage("com.whatsapp")

            startActivity(Intent.createChooser(intent, "HELP!"))
        }catch (e: Exception){
            Toast.makeText(mContext, "You may not have \"Whatsapp\" installed.", Toast.LENGTH_LONG).show()
        }
    }
}