package com.creativeprojects.medicall.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.creativeprojects.medicall.model.DoctorInboxItem
import com.creativeprojects.medicall.utils.helper.model.DoctorInboxStatus

@Dao
interface DoctorInboxDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: DoctorInboxItem)

    @Query("SELECT * from doctor_inbox_database ORDER BY date DESC")
    fun getAllItems(): LiveData<List<DoctorInboxItem>>

    @Delete
    fun delete(item: DoctorInboxItem)

    @Query("UPDATE doctor_inbox_database SET status=:status WHERE date = :date")
    fun update(status: DoctorInboxStatus, date: Long)

}