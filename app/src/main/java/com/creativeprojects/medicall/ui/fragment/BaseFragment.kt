package com.creativeprojects.medicall.ui.fragment

import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus

open class BaseFragment : Fragment() {
    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}