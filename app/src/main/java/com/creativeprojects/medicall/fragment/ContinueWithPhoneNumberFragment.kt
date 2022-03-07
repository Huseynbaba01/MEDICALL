package com.creativeprojects.medicall.fragment

import android.graphics.Path
import android.icu.text.RelativeDateTimeFormatter
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.customview.widget.Openable
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.creativeprojects.medicall.HelperClasses.SearchValidity
import com.creativeprojects.medicall.databinding.FragmentContinueWithPhoneNumberBinding
import com.creativeprojects.medicall.network.MyFirebase

class ContinueWithPhoneNumberFragment : BaseFragment() {
    lateinit var binding: FragmentContinueWithPhoneNumberBinding
    private val searchValidity: SearchValidity= SearchValidity()
    private val firebaseAuth: MyFirebase= MyFirebase(requireActivity())
    lateinit var direction : NavDirections

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContinueWithPhoneNumberBinding.inflate(inflater)
        binding.nextButton.setOnClickListener {
            if (searchValidity.isPhoneNumberValid(
                    "+" + binding.CCP,
                    binding.phoneNumber.toString()
                )
            ) {
                var phoneNumber = "+" + binding.CCP.toString() + binding.phoneNumber.toString()
                firebaseAuth.sendVerificationCode(phoneNumber)
            }
        }
        binding.nextButton.setOnClickListener(View.OnClickListener {

        })
        return binding.root
    }


}