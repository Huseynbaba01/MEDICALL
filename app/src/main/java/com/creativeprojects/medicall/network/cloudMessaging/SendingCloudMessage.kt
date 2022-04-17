package com.creativeprojects.medicall.network.cloudMessaging

import android.util.Log
import com.creativeprojects.medicall.network.methods.PushNotification
import com.creativeprojects.medicall.network.methods.RetrofitInstance
import com.creativeprojects.medicall.network.network_data.NotificationData
import com.creativeprojects.medicall.ui.fragment.TOPIC
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SendingCloudMessage {


    companion object{
        private val TAG="MyTagHere"

        fun sendMessage(textMessage:String,subTextMessage:String,date:String,largeIcon: Int) {
            PushNotification(
                NotificationData(textMessage,subTextMessage,date,largeIcon),
                TOPIC
            ).also {
                sendNotification(it)
                Log.d(TAG, "callNotification: We are here!..." )
            }
        }

        private fun sendNotification(notification:PushNotification)= CoroutineScope(Dispatchers.IO).launch{
            try{
                RetrofitInstance.api.postNotification(notification).apply {
                    if(this.isSuccessful){
                        Log.d(TAG, "sendNotification: ${Gson().toJson(this)}")
                        Log.d(TAG, "sendNotification: Successful, ${this.isSuccessful}")
                    }else{
                        Log.d(TAG, "sendNotification: ${this.errorBody().toString()}")
                        Log.d(TAG, "sendNotification: Unsuccessful, ${this.isSuccessful}")
                    }
                }

            }catch (e: Exception){
                Log.d(TAG, "sendNotification: ${e.message.toString()}")
                Log.d(TAG, "sendNotification: Exception happened!")
            }
        }
    }



}