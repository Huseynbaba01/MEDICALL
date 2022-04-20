package com.creativeprojects.medicall.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_history_database")
data class AddressHistoryItem(
    @PrimaryKey @ColumnInfo(name = "insert_time") var time : Long,
    var latitude: Double,
    var longitude: Double,
    var title: String,
    var subtitle : String
)