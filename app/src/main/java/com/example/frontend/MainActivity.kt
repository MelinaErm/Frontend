package com.example.frontend

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.frontend.databinding.ActivityMainBinding

import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "You tapped the message icon", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        //!!! changes
        //initialize AutoCompleteTextView
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view)

        // an example location suggestions with type of event and title
        val locationsWithDetails = arrayOf(
            "Athens - Music - 'Music Festival'",
            "Athens - Food - 'Food Festival'",
            "Thessaloniki - Art - 'Art Festival'"
        )

        //set up ArrayAdapter for AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, locationsWithDetails)
        autoCompleteTextView.setAdapter(adapter)

        //set up item click listener for AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            //split the selected item to extract location, event type, and title
            val details = selectedItem.split(" - ")
            val location = details[0]
            val eventType = details[1]
            val eventTitle = details[2]
            //perform any action here when a location is selected
            Snackbar.make(view, "Location: $location\nType: $eventType\nTitle: $eventTitle", Snackbar.LENGTH_LONG).show()
        }
        //!!!! end
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    } }

