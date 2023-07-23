package com.example.whattodo.data

import android.util.Log
import com.example.whattodo.R
import com.example.whattodo.model.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class DataSource {

    private val db = Firebase.firestore

    private companion object DataSource {
        private const val TAG = "DataSource"
    }

    // TODO: Implement this function
    fun loadFavoriteEvents(user: User, callback: (List<Events>) -> Unit) {
        db.collection("events")
            .get()
            .addOnSuccessListener { result ->
                val favoriteList = mutableListOf<Events>()

                //Loop through each event and "filter" any that were favorited
                for (e in result) {
                    val event = e.toObject<Events>()
                    event.id = e.id
                    if (event.id!! in user.favorites!!) {
                        favoriteList.add(event)
                    }
                }
                callback(favoriteList)
            }
    }

    fun removeFavorite(id: String, user: User, callback: () -> Unit) {
        db.collection("users")
            .document(user.id!!)
            .update("favorites", FieldValue.arrayRemove(id))
            .addOnSuccessListener {
                callback()
            }
    }

    fun addFavorite(id: String, user: User, callback: () -> Unit) {
        db.collection("users")
            .document(user.id!!)
            .update("favorites", FieldValue.arrayUnion(id))
            .addOnSuccessListener {
                callback()
            }
    }

    // get all events from firestore
    fun getEvents(callback: (List<Events>) -> Unit) {
        db.collection("events")
            .get()
            .addOnSuccessListener { result ->
                val events = mutableListOf<Events>()

                // loop over every event in result and convert to Events object
                for (e in result) {
                    val event = e.toObject<Events>()
                    event.id = e.id
                    events.add(event)
                }
                callback(events)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    // get event using firebase document id
    fun getEvent(id: String, callback: (Events) -> Unit) {
        db.collection("events")
            .document(id)
            .get()
            .addOnSuccessListener { result ->
                val event = result.toObject<Events>()
                Log.d("getEvent: ", "Event grabbed, calling callback function")
                callback(event!!)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    // add new event to firebase given the events object
    fun addEvent(event: Event) {
        db.collection("events")
            .add(event)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    //delete event from firebase using the document id
    fun deleteEvent(id: String) {
        db.collection("events")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
            }
    }

    // get all reviews from firestore for a specific event
    fun getReviews(eventID: String, callback: (List<Reviews>) -> Unit) {
        db.collection("reviews")
            .whereEqualTo("eventID", eventID)
            .get()
            .addOnSuccessListener { result ->
                val reviews = mutableListOf<Reviews>()


                // loop over every event in result and convert to Events object
                for (r in result) {
                    val review = r.toObject<Reviews>()
                    review.id = r.id
                    reviews.add(review)
                }
                callback(reviews)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    // add a review to firestore for a specific event
    fun addReview(review: Review, callback: () -> Unit) {
        db.collection("reviews")
            .add(review)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                callback()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun checkUser(user: FirebaseUser?) {
        // query user collection in database for a document with the  user's uid
        db.collection("users")
            .whereEqualTo("uid", user?.uid)
            .get()
            .addOnSuccessListener { result ->
                // if the user does not exist in the database, add them
                if (result.isEmpty) {
                    val favList = mutableListOf<String>()
                    db.collection("users")
                        .add(
                            mapOf(
                                "uid" to user?.uid,
                                "name" to user?.displayName,
                                "email" to user?.email,
                                "photoUrl" to user?.photoUrl.toString(),
                                "favorites" to favList
                            )
                        )
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getUser(uid: String, callback: (User) -> Unit) {
        var user: User? = null
        db.collection("users")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty()) {
                    // check if more than one value in result
                    if (result.size() > 1) {
                        Log.w(TAG, "More than one user with the same uid")
                        Log.w(TAG, "uid: $uid")
                        Log.w(TAG, "grabbing first")
                    }
                    val currUser = result.first()
                    //get first item in result
                    user = result.first().toObject<User>()
                    user!!.id = currUser.id
                    callback(user!!)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    // given a uid get it from the database and only return once the user has been retrieved
    

}