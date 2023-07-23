package com.example.whattodo.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.Content
import com.example.whattodo.Event
import com.example.whattodo.NavGraphDirections
import com.example.whattodo.R
import com.example.whattodo.model.Events
import java.security.cert.PKIXRevocationChecker

class EventAdapter(private val context: Context,  private val dataset: List<Events>): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val textView: TextView = view.findViewById(R.id.item_title)
        val button = view.findViewById<Button>(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)

        return EventViewHolder(adapterLayout)
    }


    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text =  item.name!!
        holder.imageView.setImageResource(getImageResource(item.image!!))

        val id = item.id.toString()
        holder.button.setOnClickListener {
            val action = NavGraphDirections.actionGlobalEvent(id)
            holder.view.findNavController().navigate(action)
        }
    }

    fun getImageResource(id:Int): Int {
        if ( id == 1) {
            return R.drawable.bar
        }
        else if (id == 2) {
            return R.drawable.axethrowing
        }
        else if (id == 3) {
            return R.drawable.bowling
        }
        else if (id == 4) {
            return R.drawable.frisbeegolf
        }
        else if (id == 5) {
            return R.drawable.putput
        }
        else if (id == 6) {
            return R.drawable.racing
        }
        else if (id == 7) {
            return R.drawable.skydiving
        }
        else if (id == 8) {
            return R.drawable.topgolf
        }
        else if (id == 9) {
            return R.drawable.concert
        }
        else if (id == 10) {
            return R.drawable.rv
        }
        else if (id == 11) {
            return R.drawable.brewlands
        }
        else if (id == 12) {
            return R.drawable.swanbrewing
        }
        else {
            return R.drawable.rv
        }
    }


    override fun getItemCount() = dataset.size


}