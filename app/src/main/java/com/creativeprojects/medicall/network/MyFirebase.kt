package com.creativeprojects.medicall.network

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.creativeprojects.medicall.fragment.ContinueWithPhoneNumberFragment
import com.creativeprojects.medicall.model.event.SendVerificationCodeEvent
import com.creativeprojects.medicall.model.event.StartActionToOTPEvent
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

class MyFirebase(activity: Activity) {


    private val TAG = "MyTagHere"
    var activity: Activity = activity
    var auth: FirebaseAuth= FirebaseAuth.getInstance()
    private val fragment: ContinueWithPhoneNumberFragment=ContinueWithPhoneNumberFragment()

    fun sendVerificationCode(countryCode:String,basePhoneNumber:String){

        val phoneNumber =""+ countryCode + basePhoneNumber

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    //It doesn't enter to this section
                    Log.d(TAG, "onVerificationCompleted: CODE SENT TO THE NUMBER:" + p0.smsCode.toString())
                }

                override fun onVerificationFailed(p0: FirebaseException) {

                    Log.d(TAG, "onVerificationFailed: "+ p0.message)

                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    EventBus.getDefault().postSticky(StartActionToOTPEvent())
                    EventBus.getDefault().postSticky(SendVerificationCodeEvent(p0))
                    super.onCodeSent(p0, p1)
                    Log.d(TAG, "onCodeSent: firstparametr:"+p0+",secondParametr:" + p1.toString())

                }


            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyPhoneNumber(view: View, credential: PhoneAuthCredential , directions: NavDirections, fragment: Fragment) {
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    NavHostFragment.findNavController(fragment).navigate(directions)
                    Log.d(TAG, "verifyPhoneNumber: is scuccessful!")
                } else {
                    Log.d(TAG, "verifyPhoneNumber: sign in is not successful!")

                }
            }
            .addOnFailureListener {
                Log.d(TAG, "verifyPhoneNumber: it didn't get success!")
            }

    }




}