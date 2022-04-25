package com.creativeprojects.medicall.ui.fragment.doctor_pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentEditProfileBinding
import com.creativeprojects.medicall.utils.preferences.PreferenceHelper
import com.creativeprojects.medicall.utils.preferences.PreferenceHelper.get
import com.creativeprojects.medicall.utils.preferences.PreferenceHelper.set

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val SELECTED_HOSPITAL = "SELECTED_HOSPITAL"
    private val DOCTOR_NAME = "DOCTOR_NAME"

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.sample_hospitals)
        )
        binding.hospitalItems.adapter = adapter
        binding.hospitalItems.setSelection(0)
        if (PreferenceHelper.getDefault(requireContext())
                .get<String>(DOCTOR_NAME) != ""
        ) binding.nameLayout.editText?.setText(
            PreferenceHelper.getDefault(requireContext()).get<String>(DOCTOR_NAME)
        )
        else binding.nameLayout.editText?.setText("Kamran Əmiraslanov")

        if (PreferenceHelper.getDefault(requireContext()).contains(SELECTED_HOSPITAL))
            binding.hospitalItems.setSelection(
                PreferenceHelper.getDefault(requireContext()).get(SELECTED_HOSPITAL)
            )
        else
            binding.hospitalItems.setSelection(2)
        binding.hospitalItems.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    PreferenceHelper.getDefault(requireContext())[SELECTED_HOSPITAL] = p2
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        binding.nameLayout.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                PreferenceHelper.getDefault(requireContext())["DOCTOR_NAME"] = p0.toString()
            }
        })
        return binding.root
    }

}