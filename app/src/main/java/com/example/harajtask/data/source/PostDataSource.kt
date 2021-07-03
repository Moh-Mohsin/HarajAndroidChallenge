package com.example.harajtask.data.source

import com.example.harajtask.data.Result
import com.example.harajtask.data.source.dto.PostDto

interface PostDataSource {

    suspend fun getAllPosts(): Result<List<PostDto>>

}