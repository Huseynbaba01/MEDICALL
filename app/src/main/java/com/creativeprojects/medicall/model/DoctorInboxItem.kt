package com.creativeprojects.medicall.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.creativeprojects.medicall.utils.helper.model.DiseaseType
import com.creativeprojects.medicall.utils.helper.model.DoctorInboxStatus

@Entity(tableName = "doctor_inbox_database")
data class DoctorInboxItem(
    @PrimaryKey val date: Long,
    var status: DoctorInboxStatus,
    val address: String,
    val cause: DiseaseType,
    val message: String?
)
