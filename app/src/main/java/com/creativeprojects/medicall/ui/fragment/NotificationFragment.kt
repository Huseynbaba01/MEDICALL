package com.creativeprojects.medicall.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentNotificationBinding
import com.creativeprojects.medicall.network.cloudMessaging.SendingCloudMessage
import com.creativeprojects.medicall.ui.dialog.DeleteMessageDialog
import com.google.firebase.messaging.FirebaseMessaging

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
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater)
        dialog = Dialog(requireContext())


        //TODO find library for FirebaseInstanceId
        FirebaseMessaging.getInstance().token.addOnCompleteListener { myToken ->
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
                binding.markAsRead.background = AppCompatResources.getDrawable(requireContext(), R.drawable.mark_as_read_blue)
                true
            } else {
                binding.markAsRead.background =  AppCompatResources.getDrawable(requireContext(), R.drawable.mark_as_read_gray)
                false
            }

        })

        binding.trash.setOnClickListener {
            val dialog = DeleteMessageDialog(requireContext())
            dialog.show(requireFragmentManager(), "customDialog")

        }

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