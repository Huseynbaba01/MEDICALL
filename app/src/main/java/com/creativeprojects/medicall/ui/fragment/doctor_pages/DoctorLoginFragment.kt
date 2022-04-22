package com.creativeprojects.medicall.ui.fragment.doctor_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentDoctorLoginBinding

class DoctorLoginFragment : Fragment() {
    private lateinit var binding: FragmentDoctorLoginBinding
    private val userName = "Doctor"
    private val password = "121212"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorLoginBinding.inflate(inflater, container, false)
        binding.login.setOnClickListener{
            if(binding.mailInputLayout.editText?.text.toString() == userName && binding.passwordInputLayout.editText?.text.toString() == password)
                findNavController().navigate(DoctorLoginFragmentDirections.actionDoctorLoginFragmentToDoctorMainactivity())
            else
                binding.passwordInputLayout.error = "İstifadəçi adı və ya şifrə yanlışdır."
        }
        return binding.root
    }
}