package com.dev.rosty.reddit.presentation.posts

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.dev.rosty.reddit.R
import com.dev.rosty.reddit.entity.Post
import com.dev.rosty.reddit.presentation.BaseFragment
import com.dev.rosty.reddit.presentation.ERROR
import com.dev.rosty.reddit.presentation.LOAD
import com.dev.rosty.reddit.presentation.image.ImageFragment
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.layout_state.*

class PostsFragment : BaseFragment<PostsViewModel>() {

    private val adapter = PostsAdapter(
            { p, v -> openImageScreen(p, v) },
            { viewModel.loadPosts() })

    override fun getLayoutRes() = R.layout.fragment_posts

    override fun getViewModelClass() = PostsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPosts.adapter = adapter
        btnRetry.setOnClickListener { viewModel.loadPosts() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadPosts()

        viewModel.posts.observe(this, Observer<List<Post>> { adapter.updateData(it!!) })
        viewModel.state.observe(this, Observer<Int> { setupState(it) })
    }

    private fun setupState(state: Int?) {

        stateError.visibility = if (state == ERROR) View.VISIBLE else View.GONE
        progress.visibility = if (state == LOAD) View.VISIBLE else View.GONE
        rvPosts.visibility = if (state != ERROR) View.VISIBLE else View.GONE
    }

    private fun openImageScreen(post: Post, view: View) {

        fragmentManager!!.beginTransaction()
                .addSharedElement(view, post.id)
                .replace(R.id.container, ImageFragment.newInstance(post.imageFull!!))
                .addToBackStack(ImageFragment::class.java.simpleName)
                .commit()
    }
}