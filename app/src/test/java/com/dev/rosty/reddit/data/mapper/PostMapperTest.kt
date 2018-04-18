package com.dev.rosty.reddit.data.mapper

import com.dev.rosty.reddit.data.remote.response.PostsResponse
import com.dev.rosty.reddit.entity.Post
import com.dev.rosty.reddit.json
import com.dev.rosty.reddit.post
import com.google.gson.Gson

import org.junit.Test

import org.junit.Assert.*

class PostMapperTest {

    @Test
    fun map() {

        val response = Gson().fromJson(json, PostsResponse::class.java)

        assertEquals(PostMapper().map(response.posts[0].data), post)
    }

}