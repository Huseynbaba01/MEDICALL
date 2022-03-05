package com.creativeprojects.medicall

import com.google.android.gms.maps.model.LatLng

data class AddressHistoryItem(
    var time : Long,
    var location : LatLng
)