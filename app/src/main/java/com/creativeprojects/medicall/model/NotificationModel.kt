package com.creativeprojects.medicall.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_table")
class NotificationModel(
    @PrimaryKey var id:Int,
    var image: Bitmap,
    var notificationMessage:String,
    var notificationDate:String
    )