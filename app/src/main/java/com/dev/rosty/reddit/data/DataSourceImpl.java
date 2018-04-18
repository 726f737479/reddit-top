package com.dev.rosty.reddit.data;

import com.dev.rosty.reddit.common.RxSchedulers;
import com.dev.rosty.reddit.data.mapper.PostMapper;
import com.dev.rosty.reddit.data.remote.RedditService;
import com.dev.rosty.reddit.data.remote.response.PostsResponse;
import com.dev.rosty.reddit.entity.Post;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class DataSourceImpl implements DataSource {

    private final RxSchedulers schedulers;
    private final RedditService service;

    public DataSourceImpl(RxSchedulers schedulers, RedditService service) {
        this.schedulers = schedulers;
        this.service = service;
    }

    @Override
    public Single<List<Post>> getPosts(String after, int count) {

        return service.getTopPosts(after, count)
                .subscribeOn(schedulers.io())
                .flatMap(new Function<PostsResponse, SingleSource<? extends List<Post>>>() {

                    @Override
                    public SingleSource<? extends List<Post>> apply(PostsResponse postsResponse) throws Exception {

                        return Observable.fromIterable(postsResponse.getPosts())
                                .map(new Function<PostsResponse.Children, Post>() {

                                    @Override
                                    public Post apply(PostsResponse.Children children) throws Exception {
                                        return new PostMapper().map(children.getData());
                                    }
                                })
                                .toList();
                    }
                });
    }
}
