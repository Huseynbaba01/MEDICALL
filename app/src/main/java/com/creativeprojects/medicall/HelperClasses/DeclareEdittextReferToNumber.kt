package com.creativeprojects.medicall.HelperClasses

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.EditText
import com.creativeprojects.medicall.databinding.FragmentOTPBinding
import com.creativeprojects.medicall.model.event.OTPBindingSentEvent
import com.google.android.material.textfield.TextInputEditText
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DeclareEdittextReferToNumber {
    lateinit var binding: FragmentOTPBinding

    fun declareEditText(order: Int?) : TextInputEditText{
        when(order){
            1 -> return binding.firstEt
            2 -> return binding.secondEt
            3 -> return binding.thirdEt
            4 -> return binding.fourthEt
            5 -> return binding.fifthEt
            6 -> return binding.sixthEt
        }
        Log.d(TAG, "declareEditText: Error happened")
        return binding.firstEt
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onSentOTPBinding(otpBindingSentEvent: OTPBindingSentEvent){
        binding=otpBindingSentEvent.binding
    }
}