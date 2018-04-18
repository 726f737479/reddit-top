package com.dev.rosty.reddit.data.remote;


import com.dev.rosty.reddit.data.remote.response.PostsResponse;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RedditService {

    String BASE_URL = "https://www.reddit.com/";

    @GET("top.json")
    Single<PostsResponse> getTopPosts(@Query("after") String after, @Query("limit") int limit);
}
