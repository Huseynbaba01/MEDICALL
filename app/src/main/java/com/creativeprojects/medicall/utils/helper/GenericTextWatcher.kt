package com.creativeprojects.medicall.utils.helper

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.android.material.textfield.TextInputEditText

class GenericTextWatcher(private val etPrev : TextInputEditText,private val etSelf:TextInputEditText, private val etNext : TextInputEditText) : TextWatcher {
    private val TAG="MyTagHere"

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        if(p0.toString().length == 1){
            Log.d(TAG, "afterTextChanged: changeTheText")
            etNext.requestFocus()
        }else if(p0.toString().length == 2){
            etSelf.setText(p0.toString()[0].toString())
            etNext.setText(p0.toString()[1].toString())
        }
        else{
            Log.d(TAG, "afterTextChanged: Don't change the text!")
            etPrev.requestFocus()
        }

    }



}