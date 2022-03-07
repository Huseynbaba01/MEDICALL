package com.creativeprojects.medicall.HelperClasses

import android.widget.EditText

class ReturnBackOTP {

    fun retrunBack(etSelf: EditText,etPrev: EditText){
        if(etSelf.toString().length==0){
            etPrev.requestFocus()
            etPrev.setText("")
        }
    }

}