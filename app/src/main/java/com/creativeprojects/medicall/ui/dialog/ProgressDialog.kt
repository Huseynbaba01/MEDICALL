package com.creativeprojects.medicall.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.creativeprojects.medicall.databinding.DialogProgressBinding

class ProgressDialog(context: Context,var text: String) : Dialog(context) {
    private lateinit var binding: DialogProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogProgressBinding.inflate(layoutInflater)
        binding.progressText.text = text
        setContentView(binding.root)
    }

    fun close(){
        dismiss()
    }
}