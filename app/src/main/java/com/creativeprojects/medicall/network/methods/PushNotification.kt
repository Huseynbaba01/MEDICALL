package com.creativeprojects.medicall.network.methods

import com.creativeprojects.medicall.network.network_data.NotificationData

data class PushNotification (
    val data: NotificationData,
    val to:String
        )