package com.creativeprojects.medicall.utils.helper

import android.Manifest
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import com.creativeprojects.medicall.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

object MapHelper {

    fun addMarkerTo(location : LatLng, googleMap: GoogleMap, context : Context){
        val markerOptions = MarkerOptions()
            .position(location)
            .icon(ImageHelper.getBitmapDescriptor(context, R.drawable.location_icon, ImageHelper.dpToPx(32), ImageHelper.dpToPx(39)))
        googleMap.addMarker(markerOptions)

        val cameraPosition = CameraPosition.Builder()
            .target(location)
            .zoom(16f)
            .bearing(0f)
            .tilt(0f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null)

    }

    fun animateCameraTo(location : LatLng, googleMap : GoogleMap, duration : Long){
        val cameraPosition = CameraPosition.Builder()
            .target(location)
            .zoom(16f)
            .bearing(0f)
            .tilt(0f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null)
    }

    fun getCurrentLocation(context : Context) : Location  {
        var location : Location? = null
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(CommonHelper.checkAndRequestForPermission(context, listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
            || CommonHelper.checkAndRequestForPermission(context, listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0f
            ) {
//                if()
                location = it
            }
        }

        return location!!
    }

    fun getLocationInfo(context : Context, location: LatLng) : Address {
        return Geocoder(context, Locale.getDefault()).getFromLocation(location.latitude, location.longitude, 1)[0]
    }

    fun getLastKnownLocation(context: Context) : Location? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (CommonHelper.checkAndRequestForPermission(
                context,
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            || CommonHelper.checkAndRequestForPermission(
                context,
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        ) {
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
        return null
    }

}