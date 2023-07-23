package com.example.whattodo.model

import com.google.firebase.firestore.GeoPoint
import java.util.*

data class Events(
    var id: String? = null,
    var image: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var date: Date? = null,
    var city: String? = null,
    var state: String? = null,
    var street: String? = null,
    var url: String? = null,
    var zip: String? = null,
    var location: GeoPoint? = null
    )
