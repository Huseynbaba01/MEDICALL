package com.creativeprojects.medicall.database.roomdb

import android.app.Application
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.creativeprojects.medicall.database.dao.NotificationDao
import com.creativeprojects.medicall.model.NotificationModel


@Database(entities = [NotificationModel::class], version = 5)
abstract class NotificationDatabase : RoomDatabase(){
    abstract fun notificationDao():NotificationDao
    companion object{

        @Volatile
        private var INSTANCE : NotificationDatabase? = null

        fun getDatabase(application: Application) : NotificationDatabase{

            if(INSTANCE != null){
                return INSTANCE!!
            }


            return Room.databaseBuilder(application.applicationContext,
                NotificationDatabase::class.java,
                "notification_database"
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }

    }
}