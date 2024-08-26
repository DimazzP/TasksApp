package com.example.tasksapp.presentation.main

import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tasksapp.R
import com.example.tasksapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniBottomView()
    }

    private fun iniBottomView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment1) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomMain
        bottomNavigationView.setupWithNavController(navController)

        viewModel.isBottomVisible.observe(this, Observer { isVisible ->
            if (isVisible) {
                binding.bottomMain.visibility = View.VISIBLE
            } else {
                binding.bottomMain.visibility = View.GONE
            }
        })
    }
}