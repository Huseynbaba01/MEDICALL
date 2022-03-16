package com.creativeprojects.medicall.event

class SendVerificationCodeEvent(verificationCode: String) {
    var verificationId = verificationCode
}