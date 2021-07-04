package com.example.harajtask.ui.postlist

import android.app.Application
import androidx.lifecycle.*
import com.example.harajtask.data.Message
import com.example.harajtask.data.Result
import com.example.harajtask.data.model.Post
import com.example.harajtask.domain.GetPostsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

class PostListViewModel(app: Application) : AndroidViewModel(app), KodeinAware {

    override val kodein by kodein(app)

    private val getPostsUseCase by instance<GetPostsUseCase>()

    private val _state: MutableLiveData<State<List<Post>>> = MutableLiveData(State.Loading)
    val state: LiveData<State<List<Post>>> = _state

    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    private val _searchQuery: MutableLiveData<String> = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _filteredPosts: MediatorLiveData<List<Post>> = MediatorLiveData<List<Post>>()

    init {
        _filteredPosts.apply {
            fun <T> filterPosts() = { _: T ->
                val query = _searchQuery.value ?: ""
                val posts = _posts.value ?: listOf()
                _filteredPosts.value =
                    if (query.isEmpty())
                        posts
                    else
                        posts.filter { it.title.contains(query) || it.city.contains(query) }
            }
            addSource(_posts, filterPosts())
            addSource(_searchQuery, filterPosts())
        }
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch {
            _state.value = State.Loading
            //simulate loading
            delay(2000)
            val result = getPostsUseCase()
            _state.value = when (result) {
                is Result.Error -> {
                    State.Error(Message.Raw(result.exception.localizedMessage ?: "Unknown error"))
                }
                is Result.Success -> {
                    _posts.value = result.data
                    State.Data(_filteredPosts)
                }
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        Timber.d("query: $query")
    }

}

sealed class State<out T> {
    data class Data<T>(val dataSource: LiveData<T>) : State<T>()
    data class Error(val message: Message) : State<Nothing>()
    object Loading : State<Nothing>()
}