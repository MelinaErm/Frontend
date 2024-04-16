package com.example.frontend

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class EventsAdapter(private val context: Context) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    private var eventsList: List<DataEventsItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(events: List<DataEventsItem>) {
        eventsList = events
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_events, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventsList[position]
        holder.bind(event)

        //set click listener to handle item click
        holder.itemView.setOnClickListener {
            //pass event details to EventDetailsActivity
            val intent = Intent(context, EventDetailsActivity::class.java).apply {
                putExtra("eventTitle", event.title)
                putExtra("eventDescription", event.description)
                putExtra("eventDate", event.date)
                putExtra("eventPrice", event.price)
                putExtra("eventCity", event.city)
                putExtra("remainingTickets", event.remaining_tickets)
                putExtra("imageUrl", event.image)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.event_title_text)
        private val dateTextView: TextView = itemView.findViewById(R.id.date_text)
        private val priceTextView: TextView = itemView.findViewById(R.id.price_text)
        private val cityTextView: TextView = itemView.findViewById(R.id.city_text)
        private val eventImageView: ImageView = itemView.findViewById(R.id.event_image)

        fun bind(event: DataEventsItem) {
            titleTextView.text = event.title
            dateTextView.text = event.date
            priceTextView.text = event.price.toString()
            cityTextView.text = event.city

            // Set background based on category
            val shadowDrawableResId = when (event.type) {
                "Music", "Art", "Sports", "Workshop" -> R.drawable.border_background2
                "Food","Dance", "Technology", "Science" -> R.drawable.border_background
                else -> R.drawable.default_shadow
            }
            itemView.setBackgroundResource(shadowDrawableResId)

            //load image using Glide
            Glide.with(itemView)
                .load(event.image)
                .placeholder(R.drawable.placeholder_image) //placeholder image while loading
                .error(R.drawable.error_image) //error image if loading fails
                .into(eventImageView)
        }
    }
}
