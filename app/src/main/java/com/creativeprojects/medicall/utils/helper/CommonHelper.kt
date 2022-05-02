package com.creativeprojects.medicall.utils.helper

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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



    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // For 29 api or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
                else ->     false
            }
        }
        // For below 29 api
        else {
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }




}