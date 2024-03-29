package com.creativeprojects.medicall.utils.helper

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat


object CommonHelper {
    fun checkAndRequestForPermission(context: Context, permissions: List<String>) : Boolean{
        Log.d("MyTagHere", "checkAndRequestForPermission: done")
        var permitted = true
        for(i in permissions.indices){
            val permission = permissions[i]
            if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                permitted = false
                if(getActivityFromContext(context) != null) {
                    ActivityCompat.requestPermissions(getActivityFromContext(context)!!, arrayOf(permission), i)
                }
            }
        }
        return permitted
    }


    fun getActivityFromContext(context: Context?): Activity? {
        if (context == null) {
            return null
        } else if (context is ContextWrapper) {
            return if (context is Activity) {
                context
            } else {
                getActivityFromContext(context.baseContext)
            }
        }
        return null
    }

    fun closeKeyboard(view: View?) {
        if (view != null) {
            val manager: InputMethodManager? = view.context.applicationContext.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager?
            manager?.hideSoftInputFromWindow(
                    view.windowToken, 0
                )
        }
    }

}