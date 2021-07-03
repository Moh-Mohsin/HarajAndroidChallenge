package com.example.harajtask.data

import com.example.harajtask.data.Result.Success


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
//            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val <T> Result<T>.succeeded
    get() = this is Success && data != null

fun <T> T.toSuccess() = Success(this)

fun <T, R> Result<T>.map(transformer: (T) -> R): Result<R> = when (this) {
    is Success -> transformer.invoke(data).toSuccess()
    is Result.Error -> Result.Error(exception)
}
fun <T, R> Result<List<T>>.mapList(transformer: (T) -> R): Result<List<R>> = when (this) {
    is Success -> data.map { transformer.invoke(it) }.toSuccess()
    is Result.Error -> Result.Error(exception)
}