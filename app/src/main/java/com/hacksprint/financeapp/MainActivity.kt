package com.hacksprint.financeapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hacksprint.financeapp.Fragments.HomeFragment
import com.hacksprint.financeapp.GraficFragment
import com.hacksprint.financeapp.ListFragment
import com.hacksprint.financeapp.ProfileFragment
import com.hacksprint.financeapp.R
import com.hacksprint.financeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
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
            binding.bottomNavigation.selectedItemId = R.id.homeIcon
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}
