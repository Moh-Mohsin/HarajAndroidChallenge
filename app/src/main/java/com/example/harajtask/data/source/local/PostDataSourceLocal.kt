package com.example.harajtask.data.source.local

import android.content.Context
import com.example.harajtask.data.Result
import com.example.harajtask.data.source.PostDataSource
import com.example.harajtask.data.source.dto.PostDto
import com.example.harajtask.data.toSuccess
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.lang.Exception

class PostDataSourceLocal(private val context: Context) : PostDataSource {
    override suspend fun getAllPosts(): Result<List<PostDto>> {
        val listOfPostsJson = context.readTextFromAsset("data.json")
        return try {
            Json.decodeFromString<List<PostDto>>(listOfPostsJson).toSuccess()
        } catch (e: Exception){
            Timber.e(e)
            Result.Error(e)
        }
    }
}


fun Context.readTextFromAsset(fileName : String) : String{
    return assets.open(fileName).bufferedReader().use {
        it.readText()}
}