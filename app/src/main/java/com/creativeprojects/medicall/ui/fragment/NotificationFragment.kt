package com.creativeprojects.medicall.ui.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.DeletemessageDialogBinding
import com.creativeprojects.medicall.databinding.FragmentNotificationBinding
import com.creativeprojects.medicall.ui.adapter.RecycNotificationAdapter
import com.creativeprojects.medicall.ui.dialog.DeleteMessageDialog

class NotificationFragment : Fragment() {
    lateinit var binding: FragmentNotificationBinding
    var isRead: Boolean = false
    lateinit var dialog: Dialog




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater)
        dialog = Dialog(requireContext())

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

    }


    private fun assignRecyclerView(){
        //TODO after getting databases we can use adapter to configure recyclerView
//        binding.myRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//        binding.myRecyclerView.adapter = RecycNotificationAdapter()

    }

}