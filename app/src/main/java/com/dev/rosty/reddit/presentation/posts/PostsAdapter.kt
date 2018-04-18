package com.dev.rosty.reddit.presentation.posts

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.rosty.reddit.R
import com.dev.rosty.reddit.entity.Post
import com.dev.rosty.reddit.util.TimeAgo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter(

        private val clickListener: (Post, View) -> Unit,
        private val paginationListener: () -> Unit

) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private var data = emptyList<Post>()

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_post, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)

        if (itemCount == position + 1) paginationListener.invoke()
    }

    fun getItem(position: Int) = data.get(position)

    fun updateData(posts: List<Post>) {
        data = posts
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post, clickListener: (Post, View) -> Unit) {

            val hasImage = post.thumbnail != null && post.imageFull != null && post.thumbnail != "default"

            itemView.transitionName = post.id

            itemView.title.text = post.title
            itemView.author.text = post.author
            itemView.date.text = TimeAgo.toRelative(System.currentTimeMillis() - post.created * 1000)
            itemView.commentCount.text = "(".plus(post.commentsCount).plus(")")
            itemView.img.visibility = if (hasImage) View.VISIBLE else View.GONE
            itemView.img.setOnClickListener { clickListener.invoke(post, itemView) }

            if (hasImage) {

                Picasso.get()
                        .load(post.thumbnail)
                        .into(itemView.img)
            }
        }
    }
}