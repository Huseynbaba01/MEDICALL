package com.creativeprojects.medicall.event

import com.creativeprojects.medicall.model.DoctorInboxItem

data class DoctorInboxProceedEvent(
    val doctorInboxItem: DoctorInboxItem,
    val position: Int
)
