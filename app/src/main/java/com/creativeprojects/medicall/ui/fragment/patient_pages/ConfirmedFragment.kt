package com.creativeprojects.medicall.ui.fragment.patient_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentConfirmedBinding


class ConfirmedFragment : Fragment() {
    lateinit var binding: FragmentConfirmedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentConfirmedBinding.inflate(inflater)
        binding.close.setOnClickListener {
            findNavController().navigate(ConfirmedFragmentDirections.actionConfirmedFragmentToApplication())
        }

        return binding.root
    }
}