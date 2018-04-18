package com.dev.rosty.reddit.data

import com.dev.rosty.reddit.common.RxSchedulers
import com.dev.rosty.reddit.data.remote.RedditService
import com.dev.rosty.reddit.data.remote.response.PostsResponse
import com.dev.rosty.reddit.entity.Post
import com.dev.rosty.reddit.json
import com.dev.rosty.reddit.post
import com.google.gson.Gson
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times
import java.util.concurrent.TimeUnit

class DataSourceImplTest {

    private lateinit var dataSource: DataSource

    @Mock
    private lateinit var redditService: RedditService


    private val scheduler  = TestScheduler()
    private val schedulers = object : RxSchedulers {

        override fun main(): Scheduler { return scheduler }

        override fun io(): Scheduler { return scheduler }
    }

    @Before
    @Throws(Exception::class) fun setUp() {

        MockitoAnnotations.initMocks(this)

        dataSource = DataSourceImpl(schedulers, redditService)
    }


    @Test
    fun getPosts() {

        val response = Gson().fromJson(json, PostsResponse::class.java)

        Mockito.`when`(redditService.getTopPosts("", 10)).thenReturn(Single.just(response))

        val observer = TestObserver<List<Post>>()

        dataSource.getPosts("", 10)
                .toObservable()
                .subscribe(observer)

        scheduler.advanceTimeBy(0, TimeUnit.SECONDS)

        val onNextEvents = observer.values()

        assertEquals(onNextEvents[0], listOf(post))

        observer.assertSubscribed()
        observer.assertNoErrors()

        Mockito.verify(redditService, Times(1)).getTopPosts("", 10)
    }
}