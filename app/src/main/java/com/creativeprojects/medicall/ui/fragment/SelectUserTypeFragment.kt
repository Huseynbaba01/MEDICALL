package com.creativeprojects.medicall.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.creativeprojects.medicall.databinding.FragmentSelectUserTypeBinding


class SelectUserTypeFragment : Fragment() {
    private lateinit var binding: FragmentSelectUserTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectUserTypeBinding.inflate(inflater)
        binding.cvCallAmbulance.setOnClickListener {
            findNavController().navigate(SelectUserTypeFragmentDirections.actionSelectUserTypeFragmentToDiseasesFragment())
        }
        binding.cvAsDoctor.setOnClickListener {
            findNavController().navigate(SelectUserTypeFragmentDirections.actionSelectUserTypeFragmentToApplication())
        }
        return binding.root

    }


}