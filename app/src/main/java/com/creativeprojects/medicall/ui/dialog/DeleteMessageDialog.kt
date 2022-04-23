package com.creativeprojects.medicall.ui.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.database.roomdb.NotificationDatabase
import com.creativeprojects.medicall.databinding.DeletemessageDialogBinding
import com.creativeprojects.medicall.databinding.FragmentNotificationBinding
import com.creativeprojects.medicall.ui.fragment.general.NotificationFragment
import com.creativeprojects.medicall.utils.mock.DoAsyncTask
import org.greenrobot.eventbus.EventBus

class DeleteMessageDialog(binding: FragmentNotificationBinding) : DialogFragment() {
    lateinit var binding :DeletemessageDialogBinding
    private val mBinding = binding
    private val TAG = "DeleteTag"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DeletemessageDialogBinding.inflate(inflater)

        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        binding.closeDialog.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        binding.noButton.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        binding.yesButton.setOnClickListener(View.OnClickListener {
            DoAsyncTask{
                activity?.let { it1 -> NotificationDatabase.getDatabase(it1.application).notificationDao().deleteAllNotifications() }

            }.run()
            mBinding.myRecyclerView.visibility = View.GONE
            mBinding.forEmptyPicture.visibility = View.VISIBLE

            mBinding.markAsRead.background = AppCompatResources.getDrawable(requireContext(), R.drawable.mark_as_read_blue)
            Log.d(TAG, "setClickListeners: View is changed")
            dismiss()
        })
    }

}