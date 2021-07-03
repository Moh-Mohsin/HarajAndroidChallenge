package com.example.harajtask.data.model

import java.io.Serializable
import java.util.*

data class Post(
    val title:String,
    val username: String,
    val thumbURL: String,
    val commentCount: Int,
    val city: String,
    val date: Date,
    val body: String,
): Serializable