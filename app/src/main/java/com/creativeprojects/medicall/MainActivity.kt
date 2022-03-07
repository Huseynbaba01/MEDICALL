package com.creativeprojects.medicall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.ui.NavigationUI
import com.creativeprojects.medicall.databinding.ActivityMainBinding
import com.creativeprojects.medicall.model.event.ConfirmVerification
import com.creativeprojects.medicall.model.event.StartActionToOTP
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun moveToOTP(startActionToOTP: StartActionToOTP){
            // Write algorithm to change the page
        Toast.makeText(this,"The confirmation has accepted!",Toast.LENGTH_LONG).show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun confirmVerification(confirmVerification: ConfirmVerification){
        //Move to another page
    }
}