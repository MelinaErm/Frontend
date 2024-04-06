package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val locationsWithDetails = arrayOf("Athens", "Thessaloniki")

        //set up ArrayAdapter for AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, locationsWithDetails)
        binding.autoCompleteTextView.setAdapter(adapter)

        //set up item click listener for AutoCompleteTextView
        binding.autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            hideKeyboard()
            val selectedItem = parent.getItemAtPosition(position) as String
            Snackbar.make(binding.root, "Location: $selectedItem", Snackbar.LENGTH_LONG).show()
            navigateToEventsActivity(selectedItem) // Pass the selected city to navigateToEventsActivity
        }

        //categories listeners
        binding.musicLl.setOnClickListener {
            Snackbar.make(binding.root, "Navigate to music events", Snackbar.LENGTH_LONG).show()
        }
        binding.foodLl.setOnClickListener {
            Snackbar.make(binding.root, "Navigate to food events", Snackbar.LENGTH_LONG).show()
        }
        binding.artLl.setOnClickListener {
            Snackbar.make(binding.root, "Navigate to art events", Snackbar.LENGTH_LONG).show()
        }
        binding.danceLl.setOnClickListener {
            Snackbar.make(binding.root, "Navigate to dance events", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun navigateToEventsActivity(selectedCity: String) {
        val intent = Intent(this, EventsActivity::class.java)
        intent.putExtra("selectedCity", selectedCity)
        startActivity(intent)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}

