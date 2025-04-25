package com.example.marylandzoo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        val btn = view.findViewById<Button>(R.id.map1)
        val btn1=view.findViewById<Button>(R.id.event1)
        val seeevent=view.findViewById<TextView>(R.id.seeevent)
        val seeAnimal=view.findViewById<TextView>(R.id.seeAnimals)
        btn.setOnClickListener {
            (activity as? MainActivity)?.setSelectedBottomNavItem(R.id.nav_map)
            btn.backgroundTintList = resources.getColorStateList(R.color.DarkGreen)
            val fragment = Map()
            val transaction = parentFragmentManager.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }
        btn1.setOnClickListener {
            (activity as? MainActivity)?.setSelectedBottomNavItem(R.id.nav_events)
            btn1.backgroundTintList = resources.getColorStateList(R.color.DarkGreen)
            val fragment = Events()
            val transaction = parentFragmentManager.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }
        seeevent.setOnClickListener {
            (activity as? MainActivity)?.setSelectedBottomNavItem(R.id.nav_events)
            seeevent.backgroundTintList = resources.getColorStateList(R.color.DarkGreen)
            val fragment = Events()
            val transaction = parentFragmentManager.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }
        seeAnimal.setOnClickListener {
            (activity as? MainActivity)?.setSelectedBottomNavItem(R.id.nav_habitats)
            seeAnimal.backgroundTintList = resources.getColorStateList(R.color.DarkGreen)
            val fragment = Habitat()
            val transaction = parentFragmentManager.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }

        return view

    }

}