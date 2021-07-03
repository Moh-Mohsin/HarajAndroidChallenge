package com.example.harajtask.ui.postdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.harajtask.R
import com.example.harajtask.databinding.PostDetailFragmentBinding
import com.example.harajtask.databinding.PostListFragmentBinding
import com.example.harajtask.ui.postlist.format
import com.example.harajtask.util.viewBinding

class PostDetailFragment : Fragment(R.layout.post_detail_fragment) {

    private val binding by viewBinding(PostDetailFragmentBinding::bind)
    val post by lazy { PostDetailFragmentArgs.fromBundle(requireArguments()).post }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.title.text = post.title
        binding.date.text = post.date.format()
        binding.username.text = post.username
        binding.city.text = post.city
        binding.body.text = post.body
    }

}