package com.creativeprojects.medicall.utils.helper

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.android.material.textfield.TextInputEditText

class GenericTextWatcher(private var etNext : TextInputEditText, private var etPrev : TextInputEditText) : TextWatcher {
    val TAG="MyTagHere"

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        when (p0.toString().length) {
            1 -> {
                Log.d(TAG, "afterTextChanged: changeTheText")
                etNext.requestFocus()
            }
            2 -> {
                Log.d(TAG, "afterTextChanged: first: ${p0!!.subSequence(0,0)} second: ${p0.toString().subSequence(1,1)}")
                etPrev.setText(p0.subSequence(0,0))
                etNext.setText(p0.toString().subSequence(1,1))
            }
            else -> {
                Log.d(TAG, "afterTextChanged: Don't change the text!")
                etPrev.requestFocus()
            }
        }

    }



}