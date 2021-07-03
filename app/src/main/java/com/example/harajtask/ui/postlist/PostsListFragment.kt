package com.example.harajtask.ui.postlist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.harajtask.R
import com.example.harajtask.data.get
import com.example.harajtask.databinding.PostListFragmentBinding
import com.example.harajtask.util.hide
import com.example.harajtask.util.show
import com.example.harajtask.util.toast
import com.example.harajtask.util.viewBinding
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein
import timber.log.Timber

class PostsListFragment : Fragment(R.layout.post_list_fragment), SearchView.OnQueryTextListener {

    private val binding by viewBinding(PostListFragmentBinding::bind)
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(PostListViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val adapter = PostsAdapter {
            val direction = PostsListFragmentDirections.toPostDetailFragment(it)
            findNavController().navigate(direction)
        }
        binding.postList.adapter = adapter

        binding.retry.setOnClickListener {
            viewModel.getPosts()
        }

        viewModel.state.observe(viewLifecycleOwner){ state ->
            Timber.d("state: $state")
            when(state){
                is State.Data -> {
                    binding.postList.show()
                    binding.loading.hide()
                    binding.retryContainer.hide()
                    state.dataSource.observe(viewLifecycleOwner){ posts ->
                        adapter.submitList(posts)
                    }
                }
                is State.Error -> {
                    binding.retryContainer.show()
                    binding.postList.hide()
                    binding.loading.hide()

                    binding.errorText.text = state.message.get(requireContext())
                }
                State.Loading -> {
                    binding.loading.show()
                    binding.retryContainer.hide()
                    binding.postList.hide()
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.post_list_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(this)
//        searchView.isIconified = false

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.setSearchQuery(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.setSearchQuery(newText)
        return true
    }

}