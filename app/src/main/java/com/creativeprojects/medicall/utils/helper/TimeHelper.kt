package com.creativeprojects.medicall.utils.helper

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object TimeHelper {
    fun getCurrentTimeInMillis() : Long{
        return Calendar.getInstance().timeInMillis
    }

    fun timeInMillisToDate(rawTime: Long): Date{
        return Date(rawTime)
    }

    @SuppressLint("SimpleDateFormat")
    fun dateToFormattedString(date: Date, format: String): String{
        return SimpleDateFormat(format).format(date)
    }
}