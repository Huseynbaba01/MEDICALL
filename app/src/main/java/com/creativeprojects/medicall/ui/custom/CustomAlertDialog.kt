package com.creativeprojects.medicall.ui.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import com.creativeprojects.medicall.databinding.DialogCustomAlertBinding
import com.creativeprojects.medicall.event.DoctorInboxItemCancelledEvent
import com.creativeprojects.medicall.model.DoctorInboxItem
import org.greenrobot.eventbus.EventBus

class CustomAlertDialog(context: Context,val item: DoctorInboxItem,val position: Int,var message: String): Dialog(context) {
    private lateinit var binding: DialogCustomAlertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCustomAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.message.text = message
        setCancelable(false)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        binding.close.setOnClickListener {
            dismiss()
        }

        binding.btnMain.setOnClickListener {
            dismiss()
        }

        binding.btnSecondary.setOnClickListener {
            //This is not general right now, you can inherit some classes to make some
            EventBus.getDefault().postSticky(DoctorInboxItemCancelledEvent(item, position))
            dismiss()//
        }
    }
}