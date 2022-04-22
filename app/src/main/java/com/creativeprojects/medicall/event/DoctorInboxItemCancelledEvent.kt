package com.creativeprojects.medicall.event

import com.creativeprojects.medicall.model.DoctorInboxItem

class DoctorInboxItemCancelledEvent(
    val item: DoctorInboxItem,
    val position: Int
)