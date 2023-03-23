package com.example.habitstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.habitstracker.data.HabitItemsDB
import com.example.habitstracker.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater,null,false)

        drawerLayout = binding.drawerLayout
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        val navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)

        val navView: NavigationView = binding.navView
        NavigationUI.setupWithNavController(navView, navController)

        setContentView(binding.root)

        devDoSomeStuff { HabitItemsDB.fillDBsample() }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)

        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}