package com.example.whattodo

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.Rating
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavArgs
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.adapter.EventAdapter
import com.example.whattodo.adapter.ReviewAdapter
import com.example.whattodo.data.DataSource
import com.example.whattodo.databinding.FragmentEventBinding
import com.example.whattodo.model.Events
import com.example.whattodo.model.Review
import com.example.whattodo.model.Reviews
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.*

class Event : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var event: Events
    private val args : EventArgs by navArgs<EventArgs>()
    private var id : String? = null
    private lateinit var recyclerView: RecyclerView
    private var user: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var dataSource: DataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataSource = DataSource()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = FirebaseAuth.getInstance()
        user = auth?.currentUser
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventAddress = view.findViewById<TextView>(R.id.eventAddress)
        val eventName = view.findViewById<TextView>(R.id.eventName)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val eventImage = view.findViewById<ImageView>(R.id.eventImage)

//      remove next line once we are passing the id
        id = args.id
        Log.v("current id: ", id.toString())

        dataSource.getEvent(id!!, fun(e: Events) {
            event = e
            eventAddress.setText(e.street + " " + e.city + " " + e.state + " " + e.zip)
            eventName.setText(e.name)
            eventImage.setImageResource(getImageResource(e.image!!))
        })

//        get all the reviews for the
        Log.v("Reviews: ", "printing the reviews")
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        dataSource.getReviews(id!!, fun(r: List<Reviews>) {
//            have a list of reviews that can be added to a recycler view
            recyclerView.adapter = ReviewAdapter(requireContext(), r)
//            Change the top rating bar to display the average rating
            var count = 0
            var totalRating: Float = count.toFloat()
            for (reviews in r) {
                Log.v("Reviews: ", reviews.id!!)
                totalRating += reviews.rating!!
                count += 1
            }
            if (count > 0) {
                totalRating /= count
            }
            ratingBar.rating = totalRating
        })

        val btn = view.findViewById<Button>(R.id.add_review)
        if (user == null) { //If the user isn't signed-in, they shouldn't see the button to add a favorite
            btn.visibility = View.GONE
        } else {
            btn?.setOnClickListener {
                var user = auth.currentUser
                if (user != null) {
                    showReviewDialog()
                }
            }
        }

        val favbtn = view.findViewById<Button>(R.id.add_favorite)
        val fragNavController = NavHostFragment.findNavController(this)
        if (user == null) { //If the user isn't signed-in, they shouldn't see the button to add a favorite
            favbtn.visibility = View.GONE
        } else {
            // Check if the current event is one of the user's favorites
            DataSource().getUser(user!!.uid) {
                if (it.favorites != null) {
                    val favInd = it.favorites!!.indexOfFirst { it == id }
                    val currUser = it
                    if (favInd != -1) { //If the current event is a favorite of the user
                        favbtn.text = "Remove Favorite"
                        favbtn.setOnClickListener {
                            Log.d("Remove Favorite", "Removing...")
                            DataSource().removeFavorite(id!!, currUser, fun () {
                                Log.d( "Remove Favorite", "Removal complete")
                                fragNavController.navigate(com.example.whattodo.R.id.action_global_content)
                            })
                        }
                    } else {
                        favbtn.text = "Add Favorite"
                        favbtn.setOnClickListener {
                            Log.d("Add Favorite", "Adding...")
                            DataSource().addFavorite(id!!, currUser, fun () {
                                Log.d( "Add Favorite", "Add complete")
                                fragNavController.navigate(com.example.whattodo.R.id.action_global_content)
                            })
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    fun showReviewDialog() {
        Log.d("Event Details", "Adding a Review")
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)

            // set the custom layout
            val customLayout: View = layoutInflater.inflate(R.layout.add_review, null)
            builder.setView(customLayout)

                // Add action buttons
            builder.setPositiveButton("Add Review", DialogInterface.OnClickListener { dialog, id ->
                        //add review
                        Log.d("Review Dialog", "Add Review")

                        var descr = customLayout.findViewById<EditText>(R.id.review_description)
                        var rating = customLayout.findViewById<RatingBar>(R.id.ratingBar)
                        addReview(descr.text.toString(), rating.rating)
                    })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                        // cancel
                        Log.d("Review Dialog", "Cancel Review")
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        alertDialog?.show()

    }

    fun addReview(desc: String, rating: Float) {
        var user = auth.currentUser
        var review = Review(id, desc, rating, user?.uid)

        dataSource.addReview(review) {
            Log.d("Added Review", "currently this only adds, and doesn't refresh list, try renavigating to page")
        }

    }

}