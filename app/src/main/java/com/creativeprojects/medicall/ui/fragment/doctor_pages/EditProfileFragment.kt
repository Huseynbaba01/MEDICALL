package com.creativeprojects.medicall.ui.fragment.doctor_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
   private lateinit var binding: FragmentEditProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.sample_hospitals))
        binding.hospitalItems.adapter = adapter
        binding.hospitalItems.setSelection(0)
        return binding.root
    }

}