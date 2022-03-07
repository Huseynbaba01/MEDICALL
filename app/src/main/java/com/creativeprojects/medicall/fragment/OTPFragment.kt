package com.creativeprojects.medicall.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DEL
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.creativeprojects.medicall.DataHolder.OrderOfEditText
import com.creativeprojects.medicall.HelperClasses.GenericTextWatcher
import com.creativeprojects.medicall.HelperClasses.ReturnBackOTP
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentOTPBinding
import com.creativeprojects.medicall.model.event.ConfirmVerification
import com.creativeprojects.medicall.model.event.VerificationCodeSentEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class OTPFragment : BaseFragment() {
    lateinit var binding: FragmentOTPBinding
    val returnBack=ReturnBackOTP()
    lateinit var verificicationCode : String
    lateinit var oderOfEdittext:OrderOfEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOTPBinding.inflate(inflater)
        passFocus();

        oderOfEdittext= OrderOfEditText()

        binding.confirmButton.setOnClickListener(View.OnClickListener {
            if(oderOfEdittext.getOrder()==6){
                var myVerificationCode=getMyVerificationCode()
                if(myVerificationCode==verificicationCode){
                    EventBus.getDefault().postSticky(ConfirmVerification())
                }
            }else{
                focusOn(oderOfEdittext.getOrder())
            }


        })

        return binding.root
    }

    private fun focusOn(order: Int?) {
        when(order){
            1 -> binding.firstEt.requestFocus()
            2 -> binding.secondEt.requestFocus()
            3 -> binding.thirdEt.requestFocus()
            4 -> binding.fourthEt.requestFocus()
            5 -> binding.fifthEt.requestFocus()
            6 -> binding.sixthEt.requestFocus()

        }
    }


    private fun getMyVerificationCode(): Any {
        var myVerificationCode=StringBuilder()
        myVerificationCode.append(binding.firstEt.toString())
        myVerificationCode.append(binding.secondEt.toString())
        myVerificationCode.append(binding.thirdEt.toString())
        myVerificationCode.append(binding.fourthEt.toString())
        myVerificationCode.append(binding.fifthEt.toString())
        myVerificationCode.append(binding.sixthEt.toString())
        return myVerificationCode
    }

    private fun passFocus() {
        binding.firstEt.addTextChangedListener(GenericTextWatcher(binding.secondEt))
        binding.secondEt.addTextChangedListener(GenericTextWatcher(binding.thirdEt))
        binding.thirdEt.addTextChangedListener(GenericTextWatcher(binding.fourthEt))
        binding.fourthEt.addTextChangedListener(GenericTextWatcher(binding.fifthEt))
        binding.fifthEt.addTextChangedListener(GenericTextWatcher(binding.sixthEt))

        binding.secondEt.setOnEditorActionListener{ v, actionId, event ->
            if(actionId == KeyEvent.KEYCODE_DEL){
                returnBack.retrunBack(binding.secondEt,binding.firstEt)
                oderOfEdittext.decreaseOrder()
                true
            } else {
                false
            }
        }
        binding.thirdEt.setOnEditorActionListener{ v, actionId, event ->
            if(actionId == KeyEvent.KEYCODE_DEL){
                returnBack.retrunBack(binding.thirdEt,binding.secondEt)
                oderOfEdittext.decreaseOrder()
                true
            } else {
                false
            }
        }
        binding.fourthEt.setOnEditorActionListener{ v, actionId, event ->
            if(actionId == KeyEvent.KEYCODE_DEL){
                returnBack.retrunBack(binding.fourthEt,binding.thirdEt)
                oderOfEdittext.decreaseOrder()
                true
            } else {
                false
            }
        }
        binding.fifthEt.setOnEditorActionListener{ v, actionId, event ->
            if(actionId == KeyEvent.KEYCODE_DEL){
                returnBack.retrunBack(binding.fifthEt,binding.fourthEt)
                oderOfEdittext.decreaseOrder()
                true
            } else {
                false
            }
        }
        binding.sixthEt.setOnEditorActionListener{ v, actionId, event ->
            if(actionId == KeyEvent.KEYCODE_DEL){
                returnBack.retrunBack(binding.sixthEt,binding.fifthEt)
                oderOfEdittext.decreaseOrder()
                true
            } else {
                false
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onVerificationCodeSentEvent(verificationCodeSentEvent: VerificationCodeSentEvent){
        verificicationCode=verificationCodeSentEvent.verificationCode
    }



}