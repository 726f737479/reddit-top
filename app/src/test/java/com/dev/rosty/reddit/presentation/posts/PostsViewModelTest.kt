package com.dev.rosty.reddit.presentation.posts

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.rosty.reddit.common.RxSchedulers
import com.dev.rosty.reddit.data.DataSource
import com.dev.rosty.reddit.posts
import com.dev.rosty.reddit.presentation.ERROR
import com.dev.rosty.reddit.presentation.RESULT
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class PostsViewModelTest {

    private lateinit var viewModel: PostsViewModel

    @Mock
    private lateinit var dataSource: DataSource

    private val scheduler  = TestScheduler()
    private val schedulers = object : RxSchedulers {

        override fun main(): Scheduler { return scheduler }

        override fun io(): Scheduler { return scheduler }
    }

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class) fun setUp() {

        MockitoAnnotations.initMocks(this)

        viewModel = PostsViewModel()
        viewModel.dataSource = dataSource
        viewModel.schedulers = schedulers
    }

    @Test
    fun loadPosts() {

        Mockito.`when`(dataSource.getPosts("", 10)).thenReturn(Single.just(posts))

        viewModel.posts.value = null
        viewModel.loadPosts()

        scheduler.advanceTimeBy(0, TimeUnit.SECONDS)

        Assert.assertEquals(viewModel.state.value, RESULT)
        Assert.assertEquals(viewModel.posts.value, posts)
    }

    @Test
    fun loadPostsEmpty() {

        Mockito.`when`(dataSource.getPosts("", 10)).thenReturn(Single.error(RuntimeException()))

        viewModel.posts.value = null
        viewModel.loadPosts()

        scheduler.advanceTimeBy(0, TimeUnit.SECONDS)

        Assert.assertEquals(viewModel.state.value, ERROR)
        Assert.assertNull(viewModel.posts.value)
    }
}