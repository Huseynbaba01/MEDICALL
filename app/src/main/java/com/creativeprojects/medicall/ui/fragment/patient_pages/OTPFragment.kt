package com.creativeprojects.medicall.ui.fragment.patient_pages

import android.annotation.SuppressLint
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
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDirections
import com.creativeprojects.medicall.databinding.FragmentOTPBinding
import com.creativeprojects.medicall.event.SendPhoneNumberAndCountryCodeEvent
import com.creativeprojects.medicall.event.SendVerificationCodeEvent
import com.creativeprojects.medicall.network.methods.MyFirebase
import com.creativeprojects.medicall.ui.fragment.general.BaseFragment
import com.creativeprojects.medicall.utils.helper.GenericTextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.PhoneAuthProvider
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class OTPFragment : BaseFragment() {
    lateinit var binding: FragmentOTPBinding
    lateinit var verificationId: String
    lateinit var phoneNumber: String
    lateinit var countryCode: String
    lateinit var firebase: MyFirebase
    lateinit var directions: NavDirections

    val TAG = "MyTagHere"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOTPBinding.inflate(inflater)
        directions = OTPFragmentDirections.actionOTPFragmentToConfirmedFragment()

        firebase = MyFirebase(requireActivity())

        startTimer()
        passFocus()
        Log.d(TAG, "onCreateView: OTPFragment")





        binding.confirmButton.setOnClickListener(View.OnClickListener {
            val myVerificationCode: String = getVerificationCode() as String
            if (myVerificationCode.length == 6) {
                searchVerification(myVerificationCode)
            } else {
                val et = checkEmptiness()
                et.requestFocus()
            }
        })

        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }

        onDeleteClicked()


        binding.idYenidenGonder.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onCreateView: yeniden gonder Clicked")
            startTimer()
            clearOTPs()
            binding.firstEt.requestFocus()
            firebase.sendVerificationCode(countryCode, phoneNumber)
            Log.d(TAG, "onCreateView: fragment restartet ")
        })

        return binding.root
    }

    private fun onDeleteClicked() {
        //TODO return previous edit text on delete key pushed if current is empty
    }

    private fun clearOTPs() {
        binding.firstEt.text!!.clear()
        binding.secondEt.text!!.clear()
        binding.thirdEt.text!!.clear()
        binding.fourthEt.text!!.clear()
        binding.fifthEt.text!!.clear()
        binding.sixthEt.text!!.clear()
    }

    @SuppressLint("SetTextI18n")
    private fun showPhoneNumberInOTP() {
        Log.d(TAG, "showPhoneNumberInOTP: yes,you did it")

        val formattedPhoneNumber: String
        if (TextUtils.isEmpty(countryCode)) {
            formattedPhoneNumber = phoneNumber
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                formattedPhoneNumber = PhoneNumberUtils.formatNumber(phoneNumber, countryCode)
                Toast.makeText(requireContext(), "Code sent to : $formattedPhoneNumber", Toast.LENGTH_SHORT)
                    .show()
            } else {
                formattedPhoneNumber = try {
                    val instance = PhoneNumberUtil.getInstance()
                    val phoneNumber = instance.parse(phoneNumber, countryCode)
                    instance.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164)

                } catch (e: NumberParseException) {
                    Log.e(TAG, "Caught: " + e.message, e)
                    phoneNumber
                }
            }
        }


        binding.secondText.text = "Kod $formattedPhoneNumber nömrəsinə göndərildi"
        Log.d(TAG, "showPhoneNumberInOTP: also that is true!..countryCode:$countryCode")
    }

    private fun startTimer() {
        object : CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val secondUntilFinished = millisUntilFinished / 1000
                if (secondUntilFinished < 10) {
                    binding.timer.text = "00:0$secondUntilFinished"
                } else {
                    binding.timer.text = "00:$secondUntilFinished"
                }

            }

            override fun onFinish() {
                Toast.makeText(requireContext(), "Your time is off!", Toast.LENGTH_LONG).show()

            }
        }.start()
    }

    private fun checkEmptiness(): TextInputEditText {
        if (binding.firstEt.text.isNullOrEmpty())
            return binding.firstEt
        else if (binding.secondEt.text.isNullOrEmpty())
            return binding.secondEt
        else if (binding.thirdEt.text.isNullOrEmpty())
            return binding.thirdEt
        else if (binding.fourthEt.text.isNullOrEmpty())
            return binding.fourthEt
        else if (binding.fifthEt.text.isNullOrEmpty())
            return binding.fifthEt
        Log.d(TAG, "checkEmpty: all full except 6")
        return binding.sixthEt
    }

    private fun  searchVerification(myVerificationCode: String) {
        Log.d(TAG, "searchVerification: getCredential Section!")
        val credential = PhoneAuthProvider.getCredential(verificationId, myVerificationCode)
        firebase.verifyPhoneNumber(binding.root, credential, directions, this)
    }

    private fun getVerificationCode(): String {
        Log.d(TAG, "getVerificationCode: +")
        val verificationCode = StringBuilder()
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
        binding.firstEt.addTextChangedListener(
            GenericTextWatcher(
                binding.firstEt,
                binding.firstEt,
                binding.secondEt
            )
        )
        binding.secondEt.addTextChangedListener(
            GenericTextWatcher(
                binding.firstEt,
                binding.secondEt,
                binding.thirdEt
            )
        )
        binding.thirdEt.addTextChangedListener(
            GenericTextWatcher(
                binding.secondEt,
                binding.thirdEt,
                binding.fourthEt
            )
        )
        binding.fourthEt.addTextChangedListener(
            GenericTextWatcher(
                binding.thirdEt,
                binding.fourthEt,
                binding.fifthEt
            )
        )
        binding.fifthEt.addTextChangedListener(
            GenericTextWatcher(
                binding.fourthEt,
                binding.fifthEt,
                binding.sixthEt
            )
        )
        binding.sixthEt.addTextChangedListener(
            GenericTextWatcher(
                binding.fifthEt,
                binding.sixthEt,
                binding.sixthEt
            ))
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onVerificationCodeSent(sendVerificationCodeEvent: SendVerificationCodeEvent) {
        verificationId = sendVerificationCodeEvent.verificationId


        Log.d(TAG, "onVerificationCodeSent: verificationCode:$verificationId")
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun getPhoneNumberAndCountryCode(sendPhoneNumberAndCountryCodeEvent: SendPhoneNumberAndCountryCodeEvent) {
//        Log.d(TAG, "getPhoneNumberAndCountryCode: getPhoneAndCountrySection")

        phoneNumber = sendPhoneNumberAndCountryCodeEvent.phoneNumber
        countryCode = sendPhoneNumberAndCountryCodeEvent.countryCode
//        Log.d(TAG, "getPhoneNumberAndCountryCode: $phoneNumber")
        showPhoneNumberInOTP()
    }



}
