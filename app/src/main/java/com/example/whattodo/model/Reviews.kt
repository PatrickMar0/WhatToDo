package com.example.whattodo.model

//To be used when getting data from firebase
data class Reviews(
    var id: String? = null,
    var eventID: String? = null,
    var description: String? = null,
    var rating: Float? = null,
    var reviewAuthor: String? = null,
)