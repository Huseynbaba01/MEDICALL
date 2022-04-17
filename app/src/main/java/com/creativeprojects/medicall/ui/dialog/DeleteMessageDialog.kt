package com.creativeprojects.medicall.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.creativeprojects.medicall.databinding.DeletemessageDialogBinding
import org.greenrobot.eventbus.EventBus

class DeleteMessageDialog : DialogFragment {
    var mContext:Context
    lateinit var binding :DeletemessageDialogBinding

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DeletemessageDialogBinding.inflate(inflater)

        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        binding.closeDialog.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        binding.noButton.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        binding.yesButton.setOnClickListener(View.OnClickListener {
            //TODO delete all figures from the list
            dismiss()
        })
    }

}