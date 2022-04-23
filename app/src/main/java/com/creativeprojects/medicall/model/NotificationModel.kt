package com.creativeprojects.medicall.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_table")
data class NotificationModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var notificationDate:String,
    var image:Int,
    var notificationMessage:String,
    var title:String,
    var read:String
    )