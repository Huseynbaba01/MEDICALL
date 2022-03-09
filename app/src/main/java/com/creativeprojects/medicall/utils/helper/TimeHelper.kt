package com.creativeprojects.medicall.utils.helper

import java.util.*

object TimeHelper {
    fun getCurrentTimeInMillis() : Long{
        return Calendar.getInstance().timeInMillis
    }
}