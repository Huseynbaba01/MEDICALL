package com.creativeprojects.medicall.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.creativeprojects.medicall.model.AddressHistoryItem
import com.creativeprojects.medicall.repository.AddressHistoryRepository
import kotlinx.coroutines.flow.Flow

class AddressSelectionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AddressHistoryRepository(application)
    val allHistoryItems : LiveData<List<AddressHistoryItem>> = repository.allHistory
    fun insertHistoryItem(addressHistoryItem: AddressHistoryItem){
        repository.insertHistoryItem(addressHistoryItem)
    }

    fun deleteHistoryItem(addressHistoryItem: AddressHistoryItem){
        repository.deleteHistoryItem(addressHistoryItem)
    }

    fun deleteAllHistoryItems(){
        repository.deleteAllHistoryItems()
    }


}