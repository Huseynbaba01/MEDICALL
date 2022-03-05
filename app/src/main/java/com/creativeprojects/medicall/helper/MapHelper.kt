package com.creativeprojects.medicall.helper

import android.content.Context
import com.creativeprojects.medicall.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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
}