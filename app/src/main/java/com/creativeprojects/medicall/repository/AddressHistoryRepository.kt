package com.creativeprojects.medicall.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.creativeprojects.medicall.database.dao.AddressHistoryDao
import com.creativeprojects.medicall.database.roomdb.MyRoomDatabase
import com.creativeprojects.medicall.model.AddressHistoryItem
import com.creativeprojects.medicall.utils.mock.DoAsyncTask
import kotlinx.coroutines.flow.Flow

class AddressHistoryRepository(application: Application) {
    private val myRoomDatabase = MyRoomDatabase.getDatabase(application)
    private val historyDao : AddressHistoryDao = myRoomDatabase.addressHistoryDao()
    val allHistory: LiveData<List<AddressHistoryItem>> = historyDao.getAllHistory()

    fun insertHistoryItem(addressHistoryItem: AddressHistoryItem){
        DoAsyncTask  {
            historyDao.insert(addressHistoryItem)
            Log.d(
                "MyTagHere20",
                "insertHistoryItem: " + addressHistoryItem.time + " " + addressHistoryItem.latitude + " " + addressHistoryItem.longitude
            )
        }.run()
    }

    fun deleteAllHistoryItems(){
        DoAsyncTask {
            historyDao.clearWholeDatabase()
        }.run()
    }

    fun deleteHistoryItem(addressHistoryItem: AddressHistoryItem){
        DoAsyncTask {
            historyDao.deleteItem(addressHistoryItem)
        }.run()
    }

    fun getHistoryItems() : LiveData<List<AddressHistoryItem>>{
        return historyDao.getAllHistory()
    }


    companion object{
       /* private class InsertHistoryItemsAsyncTask(private val addressHistoryDao: AddressHistoryDao) : AsyncTask<AddressHistoryItem, Void, Void> (){
                override fun doInBackground(vararg p0: AddressHistoryItem?): Void? {
                return null
            }

        }*/
    }

}