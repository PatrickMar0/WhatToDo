package com.example.whattodo.model

import com.google.firebase.firestore.GeoPoint
import java.util.*

data class Event(
    var image: Int? = null,
    var name: String? = "",
    var description: String? = "",
    var date: Date? = null,
    var city: String? = "",
    var state: String? = "",
    var street: String? = "",
    var url: String? = null,
    var zip: String? = "",
    var location: GeoPoint? = null
)
