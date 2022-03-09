package com.creativeprojects.medicall.database.roomdb

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.creativeprojects.medicall.database.dao.AddressHistoryDao
import com.creativeprojects.medicall.model.AddressHistoryItem

@Database(
    entities = [AddressHistoryItem::class],
    version = 1
)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun addressHistoryDao() : AddressHistoryDao
    companion object{

        @Volatile
        private var INSTANCE : MyRoomDatabase? = null

        fun getDatabase(application: Application) : MyRoomDatabase{

            if(INSTANCE != null){
                Log.d("MyTagHere26", "getDatabase: ")
                return INSTANCE!!
            }


            Log.d("MyTagHere31", "getDatabase: ")
            return Room.databaseBuilder(application.applicationContext,
                MyRoomDatabase::class.java,
                "my_database"
            ).build()
        }
    }
}