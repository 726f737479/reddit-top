package com.dev.rosty.reddit.common;

import io.reactivex.Scheduler;

public interface RxSchedulers {

    Scheduler main();

    Scheduler io();
}