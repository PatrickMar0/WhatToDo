package com.example.whattodo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.whattodo.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


data class myEvent (
    val displayName: String = "",
    val description: String = ""
)

class MainActivity : AppCompatActivity() {

    private var switch: Switch? = null
    private lateinit var navController: NavController
    private val db = Firebase.firestore
    private lateinit var  auth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        get User
        auth = FirebaseAuth.getInstance()
        user = auth?.currentUser


        //this code loads the home page and then moves to the login page immediately after to attempt
        //to get the user to sign in before preceding
        val fragNavController = findNavController(R.id.fragment)
        Handler().postDelayed({
//            this works it notices the user isn't logged in and tries to get them to log in
            if (user == null) {
                // open the favorite fragment
                fragNavController.navigate(R.id.action_global_loginFragment)
            }
        }, 2000)

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_WhatToDo)
        } else {
            setTheme(R.style.Theme_WhatToDo)
        }

        darkSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)
        val toolbar = findViewById<Toolbar>(R.id.topNavToolbar)
        val bottomAppBarConfig = AppBarConfiguration(setOf(R.id.createEvent, R.id.content, R.id.favorites))


        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            navController.navigate(R.id.action_global_content)
        }

        setupActionBarWithNavController(navController, bottomAppBarConfig)
        bottomNavView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.loginIcon -> {
            val fragNavController = findNavController(R.id.fragment)
            user = auth?.currentUser
            if (user == null) {
                fragNavController.navigate(R.id.action_global_loginFragment)
                true
            } else {
                fragNavController.navigate(R.id.action_global_settings)
                false
            }

        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



}

