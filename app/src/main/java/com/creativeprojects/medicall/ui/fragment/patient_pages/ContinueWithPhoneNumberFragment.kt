package com.creativeprojects.medicall.ui.fragment.patient_pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.creativeprojects.medicall.utils.helper.SearchValidation
import com.creativeprojects.medicall.databinding.FragmentContinueWithPhoneNumberBinding
import com.creativeprojects.medicall.event.SendPhoneNumberAndCountryCodeEvent
import com.creativeprojects.medicall.event.StartActionToOTPEvent
import com.creativeprojects.medicall.network.methods.MyFirebase
import com.creativeprojects.medicall.ui.fragment.general.BaseFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ContinueWithPhoneNumberFragment : Fragment() {
    lateinit var binding: FragmentContinueWithPhoneNumberBinding
    private lateinit var firebaseAuth: MyFirebase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContinueWithPhoneNumberBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.phoneNumber.requestFocus()
        firebaseAuth = MyFirebase(requireActivity())

        binding.CCP.setOnCountryChangeListener {
            var countryCode="("+binding.CCP.selectedCountryCode+")"
            binding.countryCode.setText(countryCode)
        }

        binding.phoneNumberReturnbackImage.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.nextButton.setOnClickListener {
            Log.d("MyTagHere", "onViewCreated: nextButtonClicked")
            if(SearchValidation.isValidPhoneNumber(binding.phoneNumber.text.toString(),"+"+binding.CCP.selectedCountryCode.toString()).isValid){
                Log.d("MyTagHere", "onViewCreated: phoneNumberIsValid")

                binding.pBar.visibility=View.VISIBLE
                Log.d("MyTagHere", "onViewCreated: phoneNumberInitialized")
                firebaseAuth.sendVerificationCode(binding.countryCode.text.toString(), binding.phoneNumber.text.toString())
                findNavController().navigate(ContinueWithPhoneNumberFragmentDirections.actionContinueWithPhoneNumberFragmentToOTPFragment(binding.phoneNumber.text.toString(),binding.countryCode.text.toString()))
                EventBus.getDefault().postSticky(SendPhoneNumberAndCountryCodeEvent(binding.CCP.selectedCountryNameCode.toString(),"(+"+binding.CCP.selectedCountryCode+")"+binding.phoneNumber.text.toString()))


            }   else{
                Toast.makeText(requireContext(),"Phone number is not valid!",Toast.LENGTH_LONG).show()
                Log.d("MyTagHere", "onViewCreated: " + binding.phoneNumber.text.toString())
            }
        }


    }

//    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
//    fun onStartActionToOTPEvent(startActionToOTP: StartActionToOTPEvent){
//        Log.d("MyTagHere", "moveToOTP: MoveToOTP")
//
//        findNavController().navigate(ContinueWithPhoneNumberFragmentDirections.actionContinueWithPhoneNumberFragmentToOTPFragment("+"+binding.countryCode+binding.phoneNumber))
//    }



}