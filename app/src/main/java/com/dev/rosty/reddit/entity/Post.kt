package com.dev.rosty.reddit.entity

data class Post(
        val id: String,
        val name: String? = "",
        val title: String? = "",
        val author: String? = "",
        val thumbnail: String? = "",
        val imageFull: String? = "",
        val commentsCount: Int = 0,
        val created: Long = 0
)