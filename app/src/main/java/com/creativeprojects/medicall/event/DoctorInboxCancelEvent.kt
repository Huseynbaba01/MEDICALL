package com.creativeprojects.medicall.event

import com.creativeprojects.medicall.model.DoctorInboxItem

data class DoctorInboxCancelEvent(
    val doctorInboxItem: DoctorInboxItem,
    val position: Int
)