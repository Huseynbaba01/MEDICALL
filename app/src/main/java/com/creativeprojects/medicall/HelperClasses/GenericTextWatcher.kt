package com.creativeprojects.medicall.HelperClasses

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.creativeprojects.medicall.databinding.FragmentOTPBinding
import com.google.android.material.textfield.TextInputEditText

class GenericTextWatcher(etNext : TextInputEditText,etPrev : TextInputEditText) : TextWatcher {
    var etNext=etNext
    var etPrev=etPrev
    val TAG="MyTagHere"

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        if(p0.toString().length == 1){
            Log.d(TAG, "afterTextChanged: changeTheText")
            etNext.requestFocus()
        }else{
            Log.d(TAG, "afterTextChanged: Don't change the text!")
            etPrev.requestFocus()
        }

    }



}