package com.creativeprojects.medicall.HelperClasses

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

class SearchValidity {

    fun isPhoneNumberValid(phoneNumber: String,countryCode: String): Boolean {
        // NOTE: This should probably be a member variable.
        val phoneUtil: PhoneNumberUtil= PhoneNumberUtil.getInstance();

        try {
             val numberProto: Phonenumber.PhoneNumber = phoneUtil.parse(phoneNumber, countryCode);
            return phoneUtil.isValidNumber(numberProto);
        } catch ( e: NumberParseException) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        return false;
    }
}