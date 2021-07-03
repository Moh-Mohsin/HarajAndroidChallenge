package com.example.harajtask.data

import com.example.harajtask.data.model.Post
import com.example.harajtask.data.source.dto.PostDto
import java.util.*


fun PostDto.toPost() = Post(title, username, thumbURL, commentCount, city, Date(date), body)