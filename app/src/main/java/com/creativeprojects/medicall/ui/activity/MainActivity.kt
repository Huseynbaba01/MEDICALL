package com.creativeprojects.medicall.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}