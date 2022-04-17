package com.creativeprojects.medicall.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.creativeprojects.medicall.model.NotificationModel


@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotificationDatas(notificationModel: NotificationModel)

    @Query("SELECT * FROM notification_table ORDER BY id DESC")
    fun getAllNotificationDatas():List<NotificationModel>
}