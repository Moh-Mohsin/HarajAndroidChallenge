package com.example.harajtask.domain

import com.example.harajtask.data.Message
import com.example.harajtask.data.Result
import com.example.harajtask.data.model.Post
import com.example.harajtask.data.repository.PostRepository
import java.lang.Exception

class GetPostsUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(): Result<List<Post>> {
        return postRepository.getAllPosts()
    }
}