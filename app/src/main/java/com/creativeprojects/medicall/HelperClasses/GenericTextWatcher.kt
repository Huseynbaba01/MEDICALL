package com.creativeprojects.medicall.HelperClasses

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.creativeprojects.medicall.DataHolder.OrderOfEditText
import com.google.android.material.textfield.TextInputEditText

class GenericTextWatcher(etNext : TextInputEditText) : TextWatcher {
    val etNext=etNext
    var orderOfEditText = OrderOfEditText()

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        if(p0.toString().length==1){
            etNext.requestFocus()
            orderOfEditText.increaseOrder()
        }
    }

}