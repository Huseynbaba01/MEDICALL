package com.creativeprojects.medicall.event

import com.creativeprojects.medicall.model.AddressHistoryItem

data class HistoryItemSelectedEvent (
    val historyItem: AddressHistoryItem
    )