package com.creativeprojects.medicall.utils.mock

import android.os.AsyncTask
import android.util.Log

class DoAsyncTask(private val runnable: Runnable) : AsyncTask<Void, Void, Void>() {
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): Void? {
        Log.d("MyTagHere", "doInBackground")
        runnable.run()
        return null
    }
    fun run(){
        execute()
    }
}