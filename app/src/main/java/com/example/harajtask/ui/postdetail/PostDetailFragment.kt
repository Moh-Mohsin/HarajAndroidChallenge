package com.example.harajtask.ui.postdetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.imageLoader
import coil.request.ImageRequest
import com.example.harajtask.R
import com.example.harajtask.databinding.PostDetailFragmentBinding
import com.example.harajtask.ui.postlist.format
import com.example.harajtask.util.shareText
import com.example.harajtask.util.viewBinding

class PostDetailFragment : Fragment(R.layout.post_detail_fragment) {

    private val binding by viewBinding(PostDetailFragmentBinding::bind)
    private val args by navArgs<PostDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val post = args.post

        postponeEnterTransition()
        binding.imageView.transitionName = getString(R.string.post_image_transition, post.title)

        val request = ImageRequest.Builder(requireContext())
            .data(post.thumbURL)
            .target { drawable ->
                binding.imageView.setImageDrawable(drawable)
                startPostponedEnterTransition()
            }
            .build()
        requireContext().imageLoader.enqueue(request)

        binding.title.text = post.title
        binding.date.text = post.date.format()
        binding.username.text = post.username
        binding.city.text = post.city
        binding.body.text = post.body
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.post_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                shareText(args.post.title)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}