package com.creativeprojects.medicall.utils.helper

import android.content.Context
import android.util.Log
import com.creativeprojects.medicall.ui.dialog.ProgressDialog

object MessageHelper {
    private lateinit var dialog: ProgressDialog

    fun showProgressDialog(context: Context, text: String){
            Log.d("MyTagHere", "showProgressDialog: 11")
            dialog = ProgressDialog(context, text)
            dialog.create()
            dialog.show()
    }

    fun closeProgressDialog(){
        if(dialog.isShowing)
            dialog.dismiss()
    }
}