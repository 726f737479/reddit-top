package com.dev.rosty.reddit.data;

import android.graphics.Bitmap;

import com.dev.rosty.reddit.entity.Post;

import java.util.List;

import io.reactivex.Single;

public interface DataSource {

    Single<List<Post>> getPosts(String after, int count);
}
