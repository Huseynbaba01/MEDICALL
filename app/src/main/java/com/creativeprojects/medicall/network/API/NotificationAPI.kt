package com.creativeprojects.medicall.network.API


import com.creativeprojects.medicall.network.methods.PushNotification
import com.creativeprojects.medicall.network.network_data.MessagingConstants.Companion.CONTENT_TYPE
import com.creativeprojects.medicall.network.network_data.MessagingConstants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {

    @Headers("Authorization: key=$SERVER_KEY","Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(@Body notification: PushNotification): Response<ResponseBody>
}