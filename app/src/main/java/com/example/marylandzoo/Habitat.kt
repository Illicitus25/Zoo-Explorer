package com.example.marylandzoo

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import androidx.core.graphics.drawable.toDrawable

data class AnimalItem(
    val id: Int,
    val name: String,
    val title: String,
    val category: String,
    val location: String,
    val drawable: Int,
    val description: String,
    val conservationStatus: String,
    val feedingTimes: String
)

class Habitat : Fragment() {
    private lateinit var buttonGroup: LinearLayout
    private lateinit var animalGrid: GridLayout
    private var selectedCategory: String = "All"
    private var selectedButtonId: Int = View.NO_ID

    private val allAnimals = listOf(
        AnimalItem(
            1, "Zoya", "Amur Tiger", "Mammals", "Big Cat Forest", R.drawable.lion,
            "Zoya is a majestic Amur tiger who enjoys sunbathing and playing with logs. She's the queen of the Big Cat Forest.",
            "Endangered",
            "11:00 AM and 4:00 PM"
        ),
        AnimalItem(
            2, "Jamal", "Giraffe", "Mammals", "Savannah Zone", R.drawable.giraffe,
            "Jamal is our tallest resident. He loves leafy greens and is a favorite among kids during feeding sessions.",
            "Vulnerable",
            "10:30 AM and 2:30 PM"
        ),
        AnimalItem(
            3, "Pebbles", "African Penguin", "Aquatic", "Penguin Coast", R.drawable.penguin,
            "Pebbles is a curious little penguin that waddles around the coast exhibit and often leads the group.",
            "Near Threatened",
            "9:30 AM and 3:00 PM"
        ),
        AnimalItem(
            4, "Kiwi", "Parrot", "Birds", "Tropical Aviary", R.drawable.parrot,
            "Kiwi mimics human voices and can repeat over 50 words. A clever bird who loves attention.",
            "Least Concern",
            "10:00 AM and 1:00 PM"
        ),
        AnimalItem(
            5, "Spike", "Iguana", "Reptiles", "Reptile House", R.drawable.iguana,
            "Spike is a laid-back iguana who loves basking under heat lamps and crunching on kale.",
            "Least Concern",
            "12:00 PM Daily"
        ),
        AnimalItem(
            6, "Milo", "Red Panda", "Mammals", "Asian Highs", R.drawable.red_panda,
            "Milo is a sleepy red panda who spends most of his time curled up in tree branches.",
            "Endangered",
            "11:30 AM and 2:00 PM"
        ),
        AnimalItem(
            7, "Sasha", "Snow Leopard", "Mammals", "Mountain Zone", R.drawable.snow_leopard,
            "Sasha is rarely seen, but she's known for her graceful movements and sharp gaze.",
            "Vulnerable",
            "2:30 PM Daily"
        ),
        AnimalItem(
            8, "Tango", "Flamingo", "Birds", "Wetlands", R.drawable.flamingo,
            "Tango stands out with her bright pink feathers and elegant dance-like walks.",
            "Least Concern",
            "9:00 AM and 1:30 PM"
        ),
        AnimalItem(
            9, "Shelly", "Tortoise", "Reptiles", "Torto Terrace", R.drawable.tortoise,
            "Shelly is our oldest zoo resident. She's over 100 years old and moves at her own pace.",
            "Protected Species",
            "10:00 AM Daily"
        ),
        AnimalItem(
            10, "Ziggy", "Zebra", "Mammals", "Savannah Zone", R.drawable.zebra,
            "Ziggy loves running around with the herd and has a unique stripe pattern you can spot from afar.",
            "Least Concern",
            "10:30 AM and 3:30 PM"
        )
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_habitat, container, false)
        buttonGroup = view.findViewById(R.id.button_group)
        animalGrid = view.findViewById(R.id.animal_grid)
        setupButtons()
        displayAnimals("All")
        return view
    }

    private fun setupButtons() {
        val categories = listOf("All", "Mammals", "Birds", "Reptiles", "Aquatic")
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
            button.id = View.generateViewId()
            buttonGroup.addView(button)

            if (category == "All") {
                button.isChecked = true
                selectedCategory = "All"
                selectedButtonId = button.id
            }

            button.setOnClickListener {
                handleButtonClick(it)
            }
        }
        updateButtonAppearance()
    }

    private fun handleButtonClick(view: View) {
        if (view is MaterialButton) {
            selectedCategory = view.tag.toString()
            selectedButtonId = view.id
            updateButtonAppearance()
            displayAnimals(selectedCategory)
        }
    }

    private fun updateButtonAppearance() {
        for (i in 0 until buttonGroup.childCount) {
            val button = buttonGroup.getChildAt(i) as MaterialButton
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

    private fun displayAnimals(category: String) {
        animalGrid.removeAllViews()
        val filteredAnimals = if (category == "All") allAnimals else allAnimals.filter { it.category == category }
        for (animal in filteredAnimals) {
            val card = createAnimalCard(animal)
            animalGrid.addView(card)
        }
    }

    private fun createAnimalCard(animal: AnimalItem): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.animal_card_item, null, false)

        val imageView = view.findViewById<ImageView>(R.id.animal_image)
        val nameView = view.findViewById<TextView>(R.id.animal_name)
        val speciesView = view.findViewById<TextView>(R.id.animal_species)
        val habitatView = view.findViewById<TextView>(R.id.animal_habitat)

        imageView.setImageResource(animal.drawable)
        nameView.text = animal.name
        speciesView.text = animal.title
        habitatView.text = animal.location

        view.setOnClickListener {
            val dialog = AnimalDetailDialogFragment(animal)
            dialog.show((view.context as AppCompatActivity).supportFragmentManager, "animalDetail")
        }

        val layoutParams = GridLayout.LayoutParams().apply {
            width = 0
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            setMargins(16, 16, 16, 16)
        }
        view.layoutParams = layoutParams

        return view
    }
}

class AnimalDetailDialogFragment(
    private val animal: AnimalItem
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.animal_dialog_details)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(android.graphics.Color.TRANSPARENT.toDrawable())

        val imageView = dialog.findViewById<ImageView>(R.id.animalImage)
        val name = dialog.findViewById<TextView>(R.id.animalName)
        val type = dialog.findViewById<TextView>(R.id.animalCategory)
        val species= dialog.findViewById<TextView>(R.id.animalType)
        val habitat = dialog.findViewById<TextView>(R.id.animalHabitat)
        val description = dialog.findViewById<TextView>(R.id.animalDescription)
        val conservation = dialog.findViewById<TextView>(R.id.animalStatus)
        val feeding = dialog.findViewById<TextView>(R.id.animalFeedingTime)
        val closeBtn = dialog.findViewById<ImageButton>(R.id.closeButton)

        imageView.setImageResource(animal.drawable)
        name.text = animal.name
        type.text = animal.title
        species.text = animal.category
        habitat.text = animal.location
        description.text= animal.description
        conservation.text = animal.conservationStatus
        feeding.text = animal.feedingTimes

        closeBtn.setOnClickListener {
            dismiss()
        }

        return dialog
    }
}
