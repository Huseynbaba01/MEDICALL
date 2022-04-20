package com.creativeprojects.medicall.ui.fragment.general

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.database.roomdb.NotificationDatabase
import com.creativeprojects.medicall.databinding.FragmentNotificationBinding
import com.creativeprojects.medicall.model.NotificationModel
import com.creativeprojects.medicall.network.cloudMessaging.SendingCloudMessage
import com.creativeprojects.medicall.network.methods.MyFirestoreForToken
import com.creativeprojects.medicall.ui.adapter.NotificationsAdapter
import com.creativeprojects.medicall.ui.dialog.DeleteMessageDialog
import com.creativeprojects.medicall.utils.mock.DoAsyncTask
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*


class NotificationFragment : Fragment() {
    lateinit var binding: FragmentNotificationBinding
    var isRead: Boolean = false

    companion object {
        const val TOPIC = "/topics/myTopic"

        private const val TAG = "MyTagHere"

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        DoAsyncTask{
            assignRecyclerView()
        }.run()


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
            val dialog = DeleteMessageDialog(binding)
            dialog.show(requireFragmentManager(), "customDialog")

        }


        binding.forInstance.setOnClickListener(View.OnClickListener {
            SendingCloudMessage.sendMessage("Warning!","Ambulance arrived!", Calendar.getInstance().timeInMillis.toString(),R.drawable.ambulance,TOPIC)
        })

    }




    private fun assignRecyclerView(){

        val notificationList = activity?.let { NotificationDatabase.getDatabase(it.application).notificationDao().getAllNotificationData() }

        if(notificationList!!.isEmpty()){
            binding.myRecyclerView.visibility = View.GONE
            binding.forEmptyPicture.visibility = View.VISIBLE
        }else {
            binding.myRecyclerView.visibility = View.VISIBLE
            binding.forEmptyPicture.visibility = View.GONE
            binding.myRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.myRecyclerView.adapter = NotificationsAdapter(notificationList,requireContext())


        }

    }




}