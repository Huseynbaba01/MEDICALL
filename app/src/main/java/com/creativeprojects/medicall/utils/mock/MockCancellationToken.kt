package com.creativeprojects.medicall.utils.mock

import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener

class MockCancellationToken : CancellationToken() {
    override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
        return this
    }

    override fun isCancellationRequested(): Boolean {
        return false
    }
}