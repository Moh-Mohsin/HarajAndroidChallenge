package com.example.harajtask.data.source.dto

@kotlinx.serialization.Serializable
data class PostDto(
    val title:String,
    val username: String,
    val thumbURL: String,
    val commentCount: Int,
    val city: String,
    val date: Long,
    val body: String,
)