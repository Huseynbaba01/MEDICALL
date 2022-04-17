package com.creativeprojects.medicall.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentNotificationBinding
import com.creativeprojects.medicall.network.methods.PushNotification
import com.creativeprojects.medicall.network.methods.RetrofitInstance
import com.creativeprojects.medicall.network.network_data.NotificationData
import com.creativeprojects.medicall.ui.dialog.DeleteMessageDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

const val TOPIC = "/topics/myTopic"
lateinit var token:String

class NotificationFragment : Fragment() {
    lateinit var binding: FragmentNotificationBinding
    var isRead: Boolean = false
    lateinit var dialog: Dialog
    private val TAG = "MyTagHere"




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater)
        dialog = Dialog(requireContext())


        //TODO find library for FirebaseInstanceId
        FirebaseMessaging.getInstance().token.addOnCompleteListener {myToken ->
            if (myToken.isSuccessful){
                Log.d(
                    TAG,
                    "MainToken: ${myToken.result}"
                )
                token = myToken.result
            }
        }

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)


        setClickListeners()

        return binding.root
    }

    private fun callNotification() {
        val title = "Main message"
        val message = "Ambulance arrived"
        PushNotification(
            NotificationData(title,message),
            TOPIC
        ).also {
            sendNotification(it)
            Log.d(TAG, "callNotification: We are here!..." )
        }
    }

    private fun setClickListeners() {
        binding.returnBackNotification.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })

        binding.markAsRead.setOnClickListener(View.OnClickListener {
            isRead = if(!isRead) {
                binding.markAsRead.setBackground(resources.getDrawable(R.drawable.mark_as_read_blue))
                true
            } else {
                binding.markAsRead.setBackground(resources.getDrawable(R.drawable.mark_as_read_gray))
                false
            }

        })

        binding.trash.setOnClickListener(View.OnClickListener {
            var dialog = DeleteMessageDialog(requireContext())
            dialog.show(requireFragmentManager(),"customDialog")

        })

        binding.forInstance.setOnClickListener(View.OnClickListener {
            //TODO Wait for writing, if you don't write delete it
        })

        binding.forInstance.setOnClickListener(View.OnClickListener {
            callNotification()
        })

    }

    private fun checkEmptiness(){
        //TODO check the emptiness of list(if empty: change the Recycler View with the imageView which is called forEmptyPicture)
    }


    private fun assignRecyclerView(){
        checkEmptiness()
        //TODO after getting databases we can use adapter to configure recyclerView
//        binding.myRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//        binding.myRecyclerView.adapter = RecycNotificationAdapter()

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

        }catch (e:Exception){
            Log.d(TAG, "sendNotification: ${e.message.toString()}")
            Log.d(TAG, "sendNotification: Exception happened!")
        }
    }

}