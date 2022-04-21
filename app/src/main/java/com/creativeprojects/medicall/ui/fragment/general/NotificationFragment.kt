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
import com.google.firebase.messaging.FirebaseMessaging
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class NotificationFragment : BaseFragment() {
    lateinit var binding: FragmentNotificationBinding

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
        Log.d(TAG, "onCreateView: firstPlace")
        DoAsyncTask{
            Log.d(TAG, "onCreateView: secondPlace")
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
            if(binding.markAsRead.background.equals(R.drawable.mark_as_read_gray)) {
                binding.markAsRead.background = AppCompatResources.getDrawable(requireContext(), R.drawable.mark_as_read_blue)
                sendNotificationRead()
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

    private fun sendNotificationRead() {
        DoAsyncTask{
            NotificationDatabase.getDatabase(requireActivity().application).notificationDao().updateAllRead()
        }.run()
    }


    private fun assignRecyclerView(){
        Log.d(TAG, "assignRecyclerView: thirdPlace")
        val notificationList = activity?.let { NotificationDatabase.getDatabase(it.application).notificationDao().getAllNotificationData() }
        Log.d(TAG, "assignRecyclerView: fourth place")
        if(notificationList!!.isEmpty()){
            binding.myRecyclerView.visibility = View.GONE
            binding.forEmptyPicture.visibility = View.VISIBLE
        }else {
            binding.myRecyclerView.visibility = View.VISIBLE
            binding.forEmptyPicture.visibility = View.GONE
            binding.myRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.myRecyclerView.adapter = NotificationsAdapter(notificationList,requireContext(),binding)


        }

    }

    @Subscribe(threadMode = ThreadMode.ASYNC,sticky = true)
    fun addingItemToRecyclerEvent(sendUniqueItemEvent: SendUniqueItemEvent){
        Log.d(TAG, "addingItemToRecyclerEvent: adapter is notified!")
        binding.myRecyclerView.adapter!!.notifyItemInserted(0)
    }


    @Subscribe(threadMode = ThreadMode.ASYNC,sticky = true)
    fun onUpdateReadHappened(updateReadEvent: UpdateReadEvent){
        Log.d(TAG, "onUpdateReadHappened: read one time happened!")
        DoAsyncTask{
            val DAO = NotificationDatabase.getDatabase(requireActivity().application).notificationDao()
            DAO.updateSingleRead(updateReadEvent.mPosition)


        }.run()
    }



}