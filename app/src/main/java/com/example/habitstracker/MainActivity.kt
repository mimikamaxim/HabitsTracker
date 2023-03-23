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
    override fun onCreate(savedInstanceState: Bundle?) {//TODO Clinuo code
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater,null,false)
        drawerLayout = binding.drawerLayout//TODO q
//        drawerLayout = findViewById(R.id.drawer_layout)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        val navView: NavigationView =
//            findViewById<NavigationView>(R.id.nav_view)
            binding.navView//TODO q
        NavigationUI.setupWithNavController(navView, navController)
//        navView.setNavigationItemSelectedListener{
//            val id = it.itemId
//            Log.i("TAG",it.toString())
//            if (id == R.id.homeFragment)
//                navController.navigate(R.id.homeFragment)
//            if (id == R.id.aboutFragment)
//                navController.navigate(R.id.aboutFragment)
//            drawerLayout.close()
//            true
//        }
        setContentView(binding.root)
        devDoSomeStuff { HabitItemsDB.fillDBsample() }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}