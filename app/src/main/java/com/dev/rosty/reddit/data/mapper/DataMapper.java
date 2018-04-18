package com.dev.rosty.reddit.data.mapper;

public interface DataMapper<F, T> {

    T map(F f);
}
