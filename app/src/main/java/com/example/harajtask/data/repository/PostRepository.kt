package com.example.harajtask.data.repository

import com.example.harajtask.data.Result
import com.example.harajtask.data.model.Post

interface PostRepository {

    suspend fun getAllPosts(): Result<List<Post>>

}