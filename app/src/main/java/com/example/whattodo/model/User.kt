package com.example.whattodo.model

data class User(
    var id: String? = null,
    var uid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var photoUrl: String? = null,
    var favorites: List<String>? = null
)