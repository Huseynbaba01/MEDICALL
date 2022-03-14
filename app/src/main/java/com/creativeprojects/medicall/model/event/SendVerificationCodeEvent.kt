package com.creativeprojects.medicall.model.event

class SendVerificationCodeEvent(verificationCode: String) {
    var verificationId = verificationCode
}