package com.creativeprojects.medicall.database.dao

import androidx.room.*
import com.creativeprojects.medicall.model.NotificationModel


@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotificationData(notificationModel: NotificationModel)

    @Query("SELECT * FROM notification_table ORDER BY notificationDate DESC")
    fun getAllNotificationData():List<NotificationModel>


    @Query("DELETE  FROM notification_table")
    fun deleteAllNotifications()

    @Query("Update notification_table set read='true'")
    fun updateAllRead()

    @Query("Update notification_table set read='true' where id=:position")
    fun updateSingleRead(position:Int)


}