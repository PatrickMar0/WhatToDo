package com.example.whattodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.R
import com.example.whattodo.data.DataSource
import com.example.whattodo.model.Events
import com.example.whattodo.model.Reviews

class ReviewAdapter(private val context: Context, private val dataset: List<Reviews>): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    class ReviewViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val desc: TextView = view.findViewById<TextView>(R.id.item_body)
        val ratingBar: RatingBar = view.findViewById<RatingBar>(R.id.item_rating)
        val user: TextView = view.findViewById<TextView>(R.id.user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ReviewViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)

        return ReviewAdapter.ReviewViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ReviewViewHolder, position: Int) {
        val item = dataset[position]
        holder.desc.text =  item.description!!
        holder.ratingBar.rating = item.rating!!
        DataSource().getUser(dataset[position].reviewAuthor!!) {
            holder.user.text = it.name
        }

    }

    override fun getItemCount() = dataset.size
}