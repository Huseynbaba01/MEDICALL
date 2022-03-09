package com.creativeprojects.medicall.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.creativeprojects.medicall.model.AddressHistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(addressHistoryItem : AddressHistoryItem)

    @Query("SELECT * from address_history_database ORDER BY insert_time DESC")
    fun getAllHistory() : LiveData<List<AddressHistoryItem>>

    @Query("DELETE FROM address_history_database")
    fun clearWholeDatabase()

    @Delete
    fun deleteItem(addressHistoryItem: AddressHistoryItem)
}