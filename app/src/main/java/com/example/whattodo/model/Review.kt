package com.example.whattodo.model


//To be used when adding data to firebase
data class Review(
    var eventID: String? = null,
    var description: String? = "",
    var rating: Float? = null,
    var reviewAuthor: String? = null,
)