package com.creativeprojects.medicall.network.cloudMessaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory.decodeResource
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.database.roomdb.NotificationDatabase
import com.creativeprojects.medicall.model.NotificationModel
import com.creativeprojects.medicall.ui.activity.MainActivity
import com.creativeprojects.medicall.ui.fragment.general.NotificationFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class ReceivingCloudMessage: FirebaseMessagingService() {
    private val channelId = "MyChannelId"
    private val channelName = "MyChannelName"
    private lateinit var token:String
    private  val TAG = "MyTagHere"


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "onMessageReceived: MessageReceived")

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )
        val pendingIntent = PendingIntent.getActivity(this,0,intent, FLAG_ONE_SHOT)

        addNotificationToLocalDatabase(message)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt()

        createNotificationChannel(notificationManager)


        val notification = NotificationCompat.Builder(this,channelId)
            .setContentIntent(pendingIntent)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSubText(message.data["date"])
            .setAutoCancel(true)
            .setChannelId(channelId)
            .setSmallIcon(R.drawable.mark_as_read_blue)
            .setLargeIcon(message.data["largeIcon"]?.let {
                decodeResource(this.resources,
                    it.toInt())
            })
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        notificationManager.notify(notificationId,notification)

    }

    private fun addNotificationToLocalDatabase(message: RemoteMessage) {


        NotificationDatabase.getDatabase(application).notificationDao().insertNotificationData(
            message.data["largeIcon"]?.let {
                NotificationModel(message.data["date"].toString(),it.toInt(),message.data["message"].toString(),message.data["title"].toString())
            }!!
        )

        showOnRecyclerView()
    }

    private fun showOnRecyclerView() {
        //TODO Show on notificationList

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