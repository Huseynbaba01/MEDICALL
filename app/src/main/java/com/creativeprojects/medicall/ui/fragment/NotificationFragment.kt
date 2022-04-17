package com.creativeprojects.medicall.ui.fragment

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentNotificationBinding
import com.creativeprojects.medicall.network.cloudMessaging.SendingCloudMessage
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
            SendingCloudMessage.sendMessage("Ambulance received","Meet if you can!","4/17/2022",R.drawable.ambulance)
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




}