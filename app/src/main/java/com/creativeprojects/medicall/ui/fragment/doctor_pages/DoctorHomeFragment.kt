package com.creativeprojects.medicall.ui.fragment.doctor_pages

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creativeprojects.medicall.databinding.DoctorHomeFragmentBinding
import com.creativeprojects.medicall.viewmodel.DoctorHomeViewModel

class DoctorHomeFragment : Fragment() {


    private lateinit var viewModel: DoctorHomeViewModel
    private lateinit var binding: DoctorHomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DoctorHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DoctorHomeViewModel::class.java]
        // TODO: Use the ViewModel
    }

}