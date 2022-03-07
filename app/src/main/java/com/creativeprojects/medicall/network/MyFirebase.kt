package com.creativeprojects.medicall.network

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.creativeprojects.medicall.model.event.VerificationCodeSentEvent
import com.creativeprojects.medicall.model.event.StartActionToOTP
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

class MyFirebase(activity: Activity) {


    var activity: Activity = activity
    var auth: FirebaseAuth= FirebaseAuth.getInstance()

    fun sendVerificationCode(phoneNumber: String){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    EventBus.getDefault().postSticky(VerificationCodeSentEvent(p0.smsCode.toString()))
                    EventBus.getDefault().postSticky(StartActionToOTP())

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    TODO("Not yet implemented")
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    Log.d(TAG, "onCodeSent: CODE SENT TO THE NUMBER")
                }


            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}