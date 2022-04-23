package com.creativeprojects.medicall.network.methods

import android.util.Log
import android.widget.Toast
import com.creativeprojects.medicall.ui.fragment.general.NotificationFragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import kotlin.coroutines.coroutineContext
import kotlin.collections.hashMapOf

class MyFirestoreForToken{




    companion object{
        val db by lazy {
            Firebase.firestore
        }


        val token by lazy {
            FirebaseMessaging.getInstance().token.addOnSuccessListener {
                Log.d(TAG, "Getting token succeeded: $it")
            }.addOnFailureListener{
                Log.d(TAG, "Getting token failed: ${it.message}")
            }
        }


        const val TAG = "MyTagHereFirestore"


    }

    private fun registerTokenAsDoctor(){



            val user = hashMapOf(
                "token" to token
            )

            db.collection("doctors")
                .add(user)
                .addOnSuccessListener {
                    Log.d(TAG, "registerTokenAsDoctor: accepted")
                }
                .addOnFailureListener {
                    Log.d(TAG, "registerTokenAsDoctor: failed")
                }


    }

    fun doIfExists():Boolean {
        db.collection("doctors").whereEqualTo("token",token).get().addOnSuccessListener { task->
            if(task.isEmpty){
                Log.d(TAG, "doIfExists: Send data to FireStore")
                registerTokenAsDoctor()
            }else{
                for(item in task.documents) {
                    Log.d(TAG, "doIfExists:Your data is exist: $item")
                }
            }
        }
            .addOnFailureListener{
                Log.d(TAG, "doIfExists: ${it.message}")
            }
        return true
    }
}