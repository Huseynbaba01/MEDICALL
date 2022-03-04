package com.creativeprojects.medicall.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentDiseasesBinding


class DiseasesFragment : Fragment() {
    private lateinit var binding: FragmentDiseasesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiseasesBinding.inflate(inflater)
        return binding.root
    }


}