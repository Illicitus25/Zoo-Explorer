package com.example.marylandzoo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar) // Initialize the Toolbar
        bottomNav = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            loadFragment(WelcomeFragment(), "Zoo Explorer") // Initial fragment and title
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(WelcomeFragment(), "Zoo Explorer")
                    true
                }

                R.id.nav_map -> {
                    loadFragment(Map(), "Zoo Map")
                    true
                }

                R.id.nav_events -> {
                    loadFragment(Events(), "Events")
                    true
                }

                R.id.nav_habitats -> {
                    loadFragment(Habitat(), "Habitats")
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        toolbar.title = title
    }
    fun setSelectedBottomNavItem(itemId: Int) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = itemId
    }

}