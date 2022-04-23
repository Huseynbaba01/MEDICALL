package com.creativeprojects.medicall.database.dao

import androidx.room.*
import com.creativeprojects.medicall.model.NotificationModel


@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotificationData(notificationModel: NotificationModel)

    @Query("SELECT * FROM notification_table ORDER BY id DESC")
    fun getAllNotificationData():List<NotificationModel>


    @Query("DELETE  FROM notification_table")
    fun deleteAllNotifications()

    @Query("Update notification_table set read=:condition")
    fun updateAllRead(condition:String)


    //TODO do not asset read to true
    @Query("Update notification_table set read=:condition where id=:position")
    fun updateSingleRead(position:Int,condition:String)



    @Query("Select read from notification_table where read=:condition")
    fun getNumberOfUnreadNotification(condition:String):List<String>



}