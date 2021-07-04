package com.example.harajtask.ui.postlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.harajtask.R
import com.example.harajtask.data.model.Post
import com.example.harajtask.databinding.SinglePostBinding
import java.text.SimpleDateFormat
import java.util.*

class PostsAdapter(
    private val onPostClicked: (
        post: Post,
        imageView: ImageView,
        imageTransitionName: String,
    ) -> Unit
) :
    ListAdapter<Post, RecyclerView.ViewHolder>(PostsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = getItem(position)
        val postViewHolder = (holder as PostViewHolder)
        postViewHolder.bind(post, position, onPostClicked)
    }

    class PostViewHolder private constructor(private val binding: SinglePostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            post: Post,
            position: Int,
            onPostClicked: (
                post: Post,
                imageView: ImageView,
                transitionName: String,
            ) -> Unit,
        ) {
            binding.title.text = post.title

            binding.date.text = post.date.format()
            binding.username.text = post.username
            binding.city.text = post.city
            binding.thumbnail.load(post.thumbURL)
            binding.thumbnail.transitionName =
                binding.root.resources.getString(R.string.post_image_transition, post.title)

            if (post.commentCount > 0) {
                binding.commentsCount.visibility = View.VISIBLE
                binding.commentsCount.text = post.commentCount.toString()
            } else {
                binding.commentsCount.visibility = View.INVISIBLE
            }
            binding.root.setOnClickListener {
                onPostClicked(
                    post,
                    binding.thumbnail,
                    binding.thumbnail.transitionName,
                )
            }
            if (position % 2 == 0) {
                binding.container.setBackgroundResource(R.drawable.post_background)
            }
        }

        companion object {
            fun from(parent: ViewGroup): PostViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SinglePostBinding
                    .inflate(layoutInflater, parent, false)
                return PostViewHolder(binding)
            }
        }
    }

    class PostsDiffCallback : DiffUtil.ItemCallback<Post>() {
        // if an id for posts was present it would be used here
        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

    }
}

fun Date.format(pattern: String = "yyyy/MM/dd h:mm a") =
    SimpleDateFormat(pattern, Locale.US).format(this)