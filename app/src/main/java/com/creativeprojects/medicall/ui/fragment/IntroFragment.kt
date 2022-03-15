package com.creativeprojects.medicall.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.creativeprojects.medicall.databinding.FragmentIntroBinding

class IntroFragment : Fragment() {
    private lateinit var binding: FragmentIntroBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(inflater, container, false)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                try {
                    findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToSelectUserTypeFragment())
                }
                catch (e : Exception){
                    e.printStackTrace()
                }
            }, 2000
        )
        return binding.root
    }
}