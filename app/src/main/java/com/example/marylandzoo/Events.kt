package com.example.marylandzoo

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.button.MaterialButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

data class EventItem(
    val id: Int,
    val title: String,
    val category: String,
    val description: String,
    val timing: String, // Added timing
    val day: String       // Added day
)

class Events : Fragment() {
    private lateinit var buttonGroup: LinearLayout
    private lateinit var eventContainer: LinearLayout
    private var allEvents: List<EventItem> = emptyList()
    private var filteredEvents: MutableList<EventItem> = mutableListOf()
    private var selectedCategory: String = "All"
    private var selectedButtonId: Int = View.NO_ID // To keep track of selected button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        buttonGroup = view.findViewById(R.id.button_group)
        eventContainer = view.findViewById(R.id.event_container)
        allEvents = listOf(
            EventItem(1, "Penguin Feeding", "Feeding", "Join our keepers for a fascinating insight into how we care for and feed our colony of Humboldt penguins.", "10:00 AM, 2:00 PM", "Daily"),
            EventItem(2, "Tiger Talk", "Talk", "Learn about our tiger conservations efforts and watch these majectic animals during their active time.", "11:30 AM", "Weekends"),
            EventItem(3, "Reptile Encounter", "Encounter", "Get up close with some of our friendlier reptiles in this hands-on educational session,", "1:00 PM", "Wed,Fri,Sun"),
            EventItem(4, "Elephant Bath Time", "Attraction", "Watch our elephants enjoy their morning bath and learn about the special care these intelligent giants require.", "9:00 AM", "Daily"),
            EventItem(5, "Nocturnal House Tour", "Tour", "A guided tour of our nocturnal house wheer you can see animals that are usually active at night.", "4:00 PM", "Tues,Thurs"),
            EventItem(6, "Conservation Talk", "Talk", "Join our conserrvation team to learn about our worldwide efforts to protect endangered species.", "3:00 PM", "Sat,Sun"),
            EventItem(7, "Giraffe Feeding", "Interactive", "Purchase a feeding token and hand-feed our friendly giraffes from our elevated platform.", "12:00 PM", "Daily"),
            EventItem(8, "Kids Safari Adventure", "Kids", "A special guided tour for children ages 5-12 with fun activities and animal facts.", "10:30 AM - 12:30 PM", "Weekends"),
            EventItem(9, "Spring Illumination", "Attraction", "After Dark! Stroll through a mesmerizing display of floral and animal themed lanterns that illuminate the night. ", "6:00 PM - 10:00 PM", "Daily"),
                EventItem(10, "Adult Prom", "Attraction", "It's time to hit the dance floor, join us in the ultimate mashup of glitter groove and giddy-up for a night of high energy fun.", "7:00 PM - 11:00 PM", "Friday"),
        )
        setupButtons()
        filterAndDisplayEvents()
        return view
    }

    private fun setupButtons() {
        val categories = listOf("All", "Feeding", "Talk", "Tour", "Interactive", "Kids", "Encounter", "Attraction")
        for ((index, category) in categories.withIndex()) {
            val button = MaterialButton(requireContext(), null, com.google.android.material.R.attr.materialButtonOutlinedStyle)
            button.text = category
            button.setTextAppearance(R.style.FilterButtonStyle)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.marginEnd = 8
            button.layoutParams = params

            button.isAllCaps = false
            button.tag = category
            button.id = View.generateViewId() // Important: Set a unique ID for each button
            buttonGroup.addView(button)

            if (category == "All") {
                button.isChecked = true
                selectedCategory = "All"
                selectedButtonId = button.id // Store the ID of the initially selected button
            }

            button.setOnClickListener { // Set click listener for each button
                handleButtonClick(it)
            }
        }
        updateButtonAppearance() // Initial call to set the correct appearance of buttons
    }

    private fun handleButtonClick(view: View) {
        if (view is MaterialButton) { // Ensure the view is a MaterialButton
            selectedCategory = view.tag.toString()
            selectedButtonId = view.id // Store the ID of the clicked button
            updateButtonAppearance()
            filterAndDisplayEvents()
        }
    }

    private fun updateButtonAppearance() {
        for (i in 0 until buttonGroup.childCount) {
            val button = buttonGroup.getChildAt(i) as MaterialButton // Cast to MaterialButton
            if (button.id == selectedButtonId) {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.Green))
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
                button.isChecked = true
            } else {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.Beige))
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.Black))
                button.isChecked = false
            }
        }
    }



    private fun filterAndDisplayEvents() {
        filteredEvents.clear()

        if (selectedCategory == "All") {
            filteredEvents.addAll(allEvents)
        } else {
            for (event in allEvents) {
                if (event.category.equals(selectedCategory, ignoreCase = true)) {
                    filteredEvents.add(event)
                }
            }
        }

        eventContainer.removeAllViews()
        for ((index, event) in filteredEvents.withIndex()) {
            val eventView = createEventView(event, index)
            eventContainer.addView(eventView)
        }
    }

    private fun createEventView(event: EventItem, position: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.event_card_item, null, false)

        // Margin values in dp
        val marginTopDp = if (position == 0) 16 else 8
        val marginBottomDp = 8
        val marginHorizontalDp = 16

        // Convert dp to pixels
        val marginTopPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            marginTopDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        val marginBottomPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            marginBottomDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        val marginHorizontalPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            marginHorizontalDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        // Apply margins
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(
            marginHorizontalPx,  // left
            marginTopPx,         // top
            marginHorizontalPx,  // right
            marginBottomPx       // bottom
        )
        view.layoutParams = layoutParams

        // Set view content
        val titleTextView = view.findViewById<TextView>(R.id.event_title)
        val descriptionTextView = view.findViewById<TextView>(R.id.event_description)
        val timingTextView = view.findViewById<TextView>(R.id.event_time)
        val dayTextView = view.findViewById<TextView>(R.id.event_day)

        titleTextView.text = event.title
        descriptionTextView.text = event.description
        timingTextView.text = event.timing
        dayTextView.text = event.day

        return view
    }

}

