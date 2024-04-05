package com.example.frontend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    private var eventsList: List<DataEventsItem> = ArrayList()

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

            //load image using Glide
            Glide.with(itemView)
                .load(event.image)
                .placeholder(R.drawable.placeholder_image) //placeholder image while loading
                .error(R.drawable.error_image) //error image if loading fails
                .into(eventImageView)
        }
    }
}
