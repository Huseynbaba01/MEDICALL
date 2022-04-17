package com.creativeprojects.medicall.network.methods

import android.util.Log
import com.creativeprojects.medicall.network.API.NotificationAPI
import com.creativeprojects.medicall.network.network_data.MessagingConstants.Companion.BASE_URL
import com.creativeprojects.medicall.ui.fragment.NotificationFragment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        private val retrofit by lazy {
            Log.d("MyTagHere", "in Retrofit builder section")
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: NotificationAPI by lazy {
            Log.d("MyTagHere", "in api section")
            retrofit.create(NotificationAPI::class.java)

        }
    }
}