package com.creativeprojects.medicall.fragment

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavDirections
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import com.creativeprojects.medicall.HelperClasses.GenericTextWatcher
import com.creativeprojects.medicall.databinding.FragmentOTPBinding
import com.creativeprojects.medicall.model.event.SendPhoneNumberAndCountryCodeEvent
import com.creativeprojects.medicall.model.event.SendVerificationCodeEvent
import com.creativeprojects.medicall.network.MyFirebase
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.PhoneAuthProvider
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class OTPFragment : BaseFragment() {
    lateinit var binding: FragmentOTPBinding
    lateinit var verificationId : String
    lateinit var phoneNumber:String
    lateinit var countryCode:String
    lateinit var firebase:MyFirebase
    lateinit var directions: NavDirections

    val TAG="MyTagHere"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOTPBinding.inflate(inflater)
        directions = OTPFragmentDirections.actionOTPFragmentToConfirmedFragment()

        firebase = MyFirebase(requireActivity())

        startTimer()
        passFocus()
        Log.d(TAG, "onCreateView: OTPFragment")





        binding.confirmButton.setOnClickListener(View.OnClickListener {
            var myVerificationCode:String = getVerificationCode() as String
            if(myVerificationCode.length==6){
                searchVerification(myVerificationCode)
            }else{
                var et=checkEmptiness()
                et.requestFocus()
            }
        })

        binding.otpReturnbackicon.setOnClickListener {
            activity?.onBackPressed()
            Log.d(TAG, "onCreateView: backButtonPressed")
        }


        binding.idYenidenGonder.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onCreateView: yeniden gonder Clicked")
            firebase.sendVerificationCode(countryCode,phoneNumber)
            //to restart fragment
            NavHostFragment.findNavController(this).navigate(OTPFragmentDirections.actionOTPFragmentSelf())
            Toast.makeText(requireContext(),"Your code sent your number again!",Toast.LENGTH_LONG).show()
            Log.d(TAG, "onCreateView: fragment restartet ")
        })

        return binding.root
    }

    private fun showPhoneNumberInOTP() {
        Log.d(TAG, "showPhoneNumberInOTP: yes,you did it")

        var formattedPhoneNumber: String
        if (TextUtils.isEmpty(countryCode)) {
            formattedPhoneNumber = phoneNumber
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                formattedPhoneNumber = PhoneNumberUtils.formatNumber(phoneNumber, countryCode)
                Toast.makeText(requireContext(), "e164 : $formattedPhoneNumber", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val instance = PhoneNumberUtil.getInstance()
                    val phoneNumber = instance.parse(phoneNumber, countryCode)
                    formattedPhoneNumber =
                        instance.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164)

                } catch (e: NumberParseException) {
                    Log.e(TAG, "Caught: " + e.message, e)
                    formattedPhoneNumber = phoneNumber
                }
            }
        }


        binding.secondText.setText("Kod "+formattedPhoneNumber + " nömrəsinə göndərildi")
        Log.d(TAG, "showPhoneNumberInOTP: also that is true!..countryCode:"+countryCode)
    }

    private fun startTimer() {
        object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var secondUntilFinished=millisUntilFinished/1000
                if(secondUntilFinished<10){
                    binding.timer.setText("00:0"+secondUntilFinished)
                }else{
                    binding.timer.setText("00:"+secondUntilFinished)
                }

            }

            override fun onFinish() {
                Toast.makeText(requireContext(),"Your time is off!",Toast.LENGTH_LONG).show()

            }
        }.start()
    }

    private fun checkEmptiness():TextInputEditText {
        if(binding.firstEt.text.isNullOrEmpty())
            return binding.firstEt
        else if(binding.secondEt.text.isNullOrEmpty())
            return binding.secondEt
        else if(binding.thirdEt.text.isNullOrEmpty())
            return binding.thirdEt
        else if(binding.fourthEt.text.isNullOrEmpty())
            return binding.fourthEt
        else if(binding.fifthEt.text.isNullOrEmpty())
            return binding.fifthEt
        Log.d(TAG, "checkEmpty: all full except 6")
        return binding.sixthEt
    }

    private fun searchVerification(myVerificationCode:String) {
        Log.d(TAG, "searchVerification: getCredential Section!")
        val credential = PhoneAuthProvider.getCredential(verificationId, myVerificationCode)
        firebase.verifyPhoneNumber(binding.root,credential,directions,this)
    }

    fun getVerificationCode(): String {
        Log.d(TAG, "getVerificationCode: +")
        var verificationCode = StringBuilder()
        verificationCode.append(binding.firstEt.text)
        verificationCode.append(binding.secondEt.text)
        verificationCode.append(binding.thirdEt.text)
        verificationCode.append(binding.fourthEt.text)
        verificationCode.append(binding.fifthEt.text)
        verificationCode.append(binding.sixthEt.text)
        Log.d(TAG, "getVerificationCode: $verificationCode")

        return verificationCode.toString()
    }


    private fun passFocus() {
        binding.firstEt.addTextChangedListener(GenericTextWatcher(binding.secondEt,binding.firstEt))
        binding.secondEt.addTextChangedListener(GenericTextWatcher(binding.thirdEt,binding.firstEt))
        binding.thirdEt.addTextChangedListener(GenericTextWatcher(binding.fourthEt,binding.secondEt))
        binding.fourthEt.addTextChangedListener(GenericTextWatcher(binding.fifthEt,binding.thirdEt))
        binding.fifthEt.addTextChangedListener(GenericTextWatcher(binding.sixthEt,binding.fourthEt))
        binding.sixthEt.addTextChangedListener(GenericTextWatcher(binding.sixthEt,binding.fifthEt))
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true)
    fun onVerificationCodeSent(sendVerificationCodeEvent: SendVerificationCodeEvent){
        verificationId=sendVerificationCodeEvent.verificationId


        Log.d(TAG, "onVerificationCodeSent: verificationCode:$verificationId")
    }


    @Subscribe(threadMode=ThreadMode.MAIN,sticky = true)
    fun getPhoneNumberAndCountryCode(sendPhoneNumberAndCountryCodeEvent: SendPhoneNumberAndCountryCodeEvent){
        Log.d(TAG, "getPhoneNumberAndCountryCode: getPhoneAndCountrySection")

        phoneNumber=sendPhoneNumberAndCountryCodeEvent.phoneNumber
        countryCode=sendPhoneNumberAndCountryCodeEvent.countryCode
        Log.d(TAG, "getPhoneNumberAndCountryCode: "+phoneNumber)
        showPhoneNumberInOTP()
    }



}