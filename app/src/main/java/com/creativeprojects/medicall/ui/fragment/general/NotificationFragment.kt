package com.creativeprojects.medicall.ui.fragment.general

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.database.roomdb.NotificationDatabase
import com.creativeprojects.medicall.databinding.FragmentNotificationBinding
import com.creativeprojects.medicall.event.SendUniqueItemEvent
import com.creativeprojects.medicall.event.UpdateReadEvent
import com.creativeprojects.medicall.network.cloudMessaging.SendingCloudMessage
import com.creativeprojects.medicall.ui.adapter.NotificationsAdapter
import com.creativeprojects.medicall.ui.dialog.DeleteMessageDialog
import com.creativeprojects.medicall.utils.mock.DoAsyncTask
import com.creativeprojects.medicall.utils.preferences.PreferenceHelper
import com.google.firebase.messaging.FirebaseMessaging
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class NotificationFragment : BaseFragment() {
    lateinit var binding: FragmentNotificationBinding
    lateinit var adapter: NotificationsAdapter

    companion object {
        const val TOPIC = "/topics/myTopic"

        private const val TAG = "MyTagHere"

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater)
        checkReads()

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        Log.d(TAG, "onCreateView: firstPlace")

        assignRecyclerView()

        setClickListeners()

        return binding.root
    }


    private fun setClickListeners() {
        binding.returnBackNotification.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })

        binding.markAsRead.setOnClickListener {
            Log.d(TAG, "setClickListeners: markAsReadSection")
            binding.markAsRead.background =
                AppCompatResources.getDrawable(requireContext(), R.drawable.mark_as_read_blue)
            sendAllNotificationRead()
            assignRecyclerView()
        }

        binding.trash.setOnClickListener {
            val dialog = DeleteMessageDialog(binding)
            dialog.show(requireFragmentManager(), "customDialog")

        }


        binding.forInstance.setOnClickListener(View.OnClickListener {
            SendingCloudMessage.sendMessage(
                "Warning!",
                "Ambulance arrived!",
                Calendar.getInstance().timeInMillis.toString(),
                R.drawable.ambulance,
                TOPIC
            )
        })

    }

    private fun sendAllNotificationRead() {
        DoAsyncTask {
            NotificationDatabase.getDatabase(requireActivity().application).notificationDao()
                .updateAllRead("true")
        }.run()
    }


    private fun assignRecyclerView() {
        val notificationList = activity?.let {
            NotificationDatabase.getDatabase(it.application).notificationDao()
                .getAllNotificationData()
        }
        Log.d(TAG, "assignRecyclerView: ")
        binding.myRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = NotificationsAdapter(notificationList, requireContext(), binding)
        binding.myRecyclerView.adapter = adapter
        if (notificationList!!.isEmpty()) {
            binding.myRecyclerView.visibility = View.GONE
            binding.forEmptyPicture.visibility = View.VISIBLE
        } else {
            binding.myRecyclerView.visibility = View.VISIBLE
            binding.forEmptyPicture.visibility = View.GONE


        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun addingItemToRecyclerEvent(sendUniqueItemEvent: SendUniqueItemEvent) {
        val notificationList = activity?.let {
            NotificationDatabase.getDatabase(it.application).notificationDao()
                .getAllNotificationData()
        }
        if (notificationList!!.size == 1) {
            binding.myRecyclerView.visibility = View.VISIBLE
            binding.forEmptyPicture.visibility = View.GONE
            assignRecyclerView()
        }
        Log.d(TAG, "addingItemToRecyclerEvent: " + notificationList!!.size)
        adapter.notificationList = notificationList
        adapter.notifyItemInserted(0)
        binding.markAsRead.background =
            AppCompatResources.getDrawable(requireContext(), R.drawable.mark_as_read_gray)
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onUpdateReadHappened(updateReadEvent: UpdateReadEvent) {
            val DAO = NotificationDatabase.getDatabase(requireActivity().application).notificationDao()
            Log.d(TAG, "onUpdateReadHappened: recyclerPosition:"+updateReadEvent.mPosition)
            DAO.updateSingleRead(updateReadEvent.mPosition, "true")

        checkReads()

    }

    private fun checkReads() {
        val list: List<String> =
            NotificationDatabase.getDatabase(requireActivity().application).notificationDao()
                .getNumberOfUnreadNotification("false")
        Log.d(TAG, "checkReads: My False List is:$list")
        if (list.isEmpty())
            binding.markAsRead.background =
                AppCompatResources.getDrawable(requireContext(), R.drawable.mark_as_read_blue)
        else {
            binding.markAsRead.background =
                AppCompatResources.getDrawable(requireContext(), R.drawable.mark_as_read_gray)
        }
    }


}