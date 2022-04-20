package com.creativeprojects.medicall.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.ActivityDoctorMainBinding

class DoctorMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setBackgroundColor(getColor(android.R.color.transparent))
        appBarConfiguration = AppBarConfiguration(setOf(R.drawable.home_icon, R.drawable.pin_icon, R.drawable.user_icon))
        navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}