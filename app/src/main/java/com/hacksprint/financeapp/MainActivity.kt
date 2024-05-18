package com.hacksprint.financeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeIcon -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.listaIcon -> {
                    replaceFragment(ListFragment())
                    true
                }

                R.id.graficIcon -> {
                    replaceFragment(GraficFragment())
                    true
                }

                R.id.profileIcon -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> false

            }
        }

    if (savedInstanceState == null) {
        replaceFragment(HomeFragment())
        bottomNavigation.selectedItemId = R.id.homeIcon
    }
}
    private fun  replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}
