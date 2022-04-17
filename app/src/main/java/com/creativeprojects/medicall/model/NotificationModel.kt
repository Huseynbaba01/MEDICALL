package com.creativeprojects.medicall.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_table")
class NotificationModel(
    @PrimaryKey var id:Int,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray,
    var notificationMessage:String,
    var notificationDate:String
    )