package com.example.harajtask.data.repository.impl

import com.example.harajtask.data.Result
import com.example.harajtask.data.mapList
import com.example.harajtask.data.model.Post
import com.example.harajtask.data.repository.PostRepository
import com.example.harajtask.data.source.PostDataSource
import com.example.harajtask.data.toPost

class PostRepositoryImpl(private val postDataSource: PostDataSource) : PostRepository {
    override suspend fun getAllPosts(): Result<List<Post>> {
        return postDataSource.getAllPosts().mapList { it.toPost() }
    }
}