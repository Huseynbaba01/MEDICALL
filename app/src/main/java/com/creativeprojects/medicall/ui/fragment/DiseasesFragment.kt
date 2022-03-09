package com.creativeprojects.medicall.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentDiseasesBinding


class DiseasesFragment : Fragment() {
    private lateinit var binding: FragmentDiseasesBinding
    private var corona:Boolean? = null
    private var carCrash:Boolean? = null
    private var dizzy:Boolean? = null
    private var fever:Boolean? = null
    private var heartStroke:Boolean? = null
    private var bleeding:Boolean? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        corona = true
        fever = true
        carCrash = true
        dizzy = true
        heartStroke = true
        bleeding = true
        binding = FragmentDiseasesBinding.inflate(inflater)

        binding.corona?.setOnClickListener {
            corona = if(corona as Boolean) {
                binding.corona!!.setBackgroundResource(R.drawable.diseases_clicked_background)
                false
            } else {
                binding.corona!!.setBackgroundResource(R.drawable.diseases_background)
                true
            }
        }

        binding.carCrash?.setOnClickListener {
            carCrash = if(carCrash as Boolean) {
                binding.carCrash!!.setBackgroundResource(R.drawable.diseases_clicked_background)
                false
            } else {
                binding.carCrash!!.setBackgroundResource(R.drawable.diseases_background)
                true
            }
        }

        binding.dizzy?.setOnClickListener {
            dizzy = if(dizzy as Boolean) {
                binding.dizzy!!.setBackgroundResource(R.drawable.diseases_clicked_background)
                false

            } else {
                binding.dizzy!!.setBackgroundResource(R.drawable.diseases_background)
                true
            }
        }

        binding.fever?.setOnClickListener {
            fever = if(fever as Boolean) {
                binding.fever!!.setBackgroundResource(R.drawable.diseases_clicked_background)
                false
            } else {
                binding.fever!!.setBackgroundResource(R.drawable.diseases_background)
                true
            }
        }

        binding.heartStorke?.setOnClickListener {
            heartStroke = if(heartStroke as Boolean) {
                binding.heartStorke!!.setBackgroundResource(R.drawable.diseases_clicked_background)
                false
            } else {
                binding.heartStorke!!.setBackgroundResource(R.drawable.diseases_background)
                true
            }
        }

        binding.bleeding?.setOnClickListener {
            bleeding = if(bleeding as Boolean) {
                binding.bleeding!!.setBackgroundResource(R.drawable.diseases_clicked_background)
                false
            } else {
                binding.bleeding!!.setBackgroundResource(R.drawable.diseases_background)
                true
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.arrowLeft!!.setOnClickListener {
            findNavController().navigate(DiseasesFragmentDirections.actionDiseasesFragmentToSelectUserTypeFragment())
        }
        super.onViewCreated(view, savedInstanceState)
    }


}