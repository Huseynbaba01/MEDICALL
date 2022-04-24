package com.creativeprojects.medicall.ui.fragment.patient_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentDiseasesBinding
import com.creativeprojects.medicall.utils.helper.model.Disease
import com.creativeprojects.medicall.utils.helper.model.DiseaseType.*


class DiseasesFragment : Fragment(), View.OnFocusChangeListener {


    private lateinit var binding: FragmentDiseasesBinding
    private val corona = Disease(CORONA)
    private val carCrash = Disease(CAR_CRASH)
    private val dizzy = Disease(DIZZY)
    private val fever = Disease(FEVER)
    private val heartStroke = Disease(HEART_STROKE)
    private val bleeding = Disease(BLEEDING)
    private val other = Disease(OTHER)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiseasesBinding.inflate(inflater)
        binding.mtEnterReason.editText?.onFocusChangeListener = this

        // making  diseases change background when clicked
        binding.corona?.setOnClickListener {
            selectItem(it, corona)
        }

        binding.carCrash?.setOnClickListener {
            selectItem(it, carCrash)
        }

        binding.dizzy?.setOnClickListener {
            selectItem(it, dizzy)
        }

        binding.fever?.setOnClickListener {
            selectItem(it, fever)

        }

        binding.heartStroke?.setOnClickListener {
            selectItem(it, heartStroke)
        }

        binding.bleeding?.setOnClickListener {
            selectItem(it, bleeding)
        }

        return binding.root
    }

    private fun selectItem(item: View, disease: Disease){
        disease.selected = if(!disease.selected) {
            clearOthersBesides(disease)
            item.setBackgroundResource(R.drawable.diseases_clicked_background)
            true
        } else {
            item.setBackgroundResource(R.drawable.diseases_background)
            false
        }
        clearTextBox()
    }

    private fun clearOthersBesides(disease: Disease){
        if(disease != corona) {
            binding.corona?.setBackgroundResource(R.drawable.diseases_background)
            corona.selected = false
        }
        if(disease != carCrash) {
        binding.carCrash?.setBackgroundResource(R.drawable.diseases_background)
        carCrash.selected = false
        }
        if(disease != dizzy) {
        binding.dizzy?.setBackgroundResource(R.drawable.diseases_background)
        dizzy.selected = false
        }
        if(disease != fever) {
        binding.fever?.setBackgroundResource(R.drawable.diseases_background)
        fever.selected = false
        }
        if(disease != heartStroke) {
        binding.heartStroke?.setBackgroundResource(R.drawable.diseases_background)
        heartStroke.selected = false
        }
        if(disease != bleeding) {
        binding.bleeding?.setBackgroundResource(R.drawable.diseases_background)
        bleeding.selected = false
        }
    }

    private fun clearTextBox(){
        binding.mtEnterReason.editText?.setText("")
        binding.mtEnterReason.editText?.clearFocus()
    }

    // return back when arrow clicked
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.back?.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.mbConfirm.setOnClickListener {
            findNavController().navigate(DiseasesFragmentDirections.actionDiseasesFragmentToContinueWithPhoneNumberFragment())
        }
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onFocusChange(et: View?, p1: Boolean) {
        if (et?.hasFocus() == true)
            clearOthersBesides(other)
    }
}