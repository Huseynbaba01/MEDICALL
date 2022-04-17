package com.creativeprojects.medicall.network.cloudMessaging

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.ui.fragment.NotificationFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class ReceivingCloudMessage: FirebaseMessagingService() {
    private val channelId = "MyChannelId"
    private val channelName = "MyChannelName"
    private lateinit var token:String
    private val TAG:String = "MyTagHereMain"


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "onMessageReceived: MessageReceived")

        val intent = Intent(this, NotificationFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent, FLAG_ONE_SHOT)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt()

        createNotificationChannel(notificationManager)


        val notification = NotificationCompat.Builder(this,channelId)
            .setContentIntent(pendingIntent)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setAutoCancel(true)
            .setChannelId(channelId)
            .setSmallIcon(R.drawable.mark_as_read_blue)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        notificationManager.notify(notificationId,notification)

    }


    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val notificationChannel = NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.description = "my channel description"
        notificationChannel.enableLights(true)
        notificationChannel.lightColor= Color.BLUE

        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        this.token =token
    }
}