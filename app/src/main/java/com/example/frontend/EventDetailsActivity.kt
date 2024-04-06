package com.example.frontend

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class EventDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        //retrieve event details from intent extras
        val eventTitle = intent.getStringExtra("eventTitle")
        val eventDate = intent.getStringExtra("eventDate")
        val eventPrice = intent.getIntExtra("eventPrice", 0)
        val eventCity = intent.getStringExtra("eventCity")
        val remainingTickets = intent.getIntExtra("remainingTickets", 0)
        val eventDescription = intent.getStringExtra("eventDescription")
        val imageUrl = intent.getStringExtra("imageUrl") // Retrieve image URL

        //get references to views
        val eventTitleTextView: TextView = findViewById(R.id.event_title_text)
        val eventDateTextView: TextView = findViewById(R.id.date_text)
        val eventPriceTextView: TextView = findViewById(R.id.price_text)
        val eventCityTextView: TextView = findViewById(R.id.city_text)
        val remainingTicketsTextView: TextView = findViewById(R.id.remaining_tickets_text)
        val eventDescriptionTextView: TextView = findViewById(R.id.event_description_text)
        val eventImageView: ImageView = findViewById(R.id.event_image)
        val btnSendTicket: Button = findViewById(R.id.btnSendTicket)

        //set event details to TextViews
        eventTitleTextView.text = eventTitle
        eventDateTextView.text = eventDate
        eventPriceTextView.text = "$eventPrice €"
        eventCityTextView.text = eventCity
        remainingTicketsTextView.text = remainingTickets.toString()
        eventDescriptionTextView.text = eventDescription

        //load image using Glide
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
            .error(R.drawable.error_image) // Error image if loading fails
            .into(eventImageView)

        //set up button click listener
        btnSendTicket.setOnClickListener {
            sendTicket()
        }
    }

    private fun sendTicket() {
        //display a message indicating that the ticket has been sent
        Toast.makeText(this, "Ticket sent!", Toast.LENGTH_SHORT).show()
    }
}
