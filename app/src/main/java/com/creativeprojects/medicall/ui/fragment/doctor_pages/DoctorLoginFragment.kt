package com.creativeprojects.medicall.ui.fragment.doctor_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentDoctorLoginBinding

class DoctorLoginFragment : Fragment() {
    private lateinit var binding: FragmentDoctorLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorLoginBinding.inflate(inflater, container, false)

        return binding.root
    }
}