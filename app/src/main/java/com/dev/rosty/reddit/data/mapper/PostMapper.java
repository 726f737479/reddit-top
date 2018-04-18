package com.dev.rosty.reddit.data.mapper;

import com.dev.rosty.reddit.data.remote.response.PostsResponse;
import com.dev.rosty.reddit.entity.Post;

public class PostMapper implements DataMapper<PostsResponse.Data, Post> {

    @Override
    public Post map(PostsResponse.Data data) {

        return new Post(
                data.getId(),
                data.getName(),
                data.getTitle(),
                data.getAuthor(),
                data.getThumbnail(),
                data.getUrl(),
                data.getNumComments(),
                data.getCreated());
    }
}
