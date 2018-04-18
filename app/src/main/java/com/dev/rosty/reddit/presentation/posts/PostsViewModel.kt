package com.dev.rosty.reddit.presentation.posts

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.dev.rosty.reddit.common.RxSchedulers
import com.dev.rosty.reddit.data.DataSource
import com.dev.rosty.reddit.entity.Post
import com.dev.rosty.reddit.injection.AppComponent
import com.dev.rosty.reddit.presentation.ERROR
import com.dev.rosty.reddit.presentation.LOAD
import com.dev.rosty.reddit.presentation.RESULT

import javax.inject.Inject

import io.reactivex.disposables.Disposable

class PostsViewModel : ViewModel(), AppComponent.Injectable {

    private val LOUD_ITEMS_COUNT = 10
    private val MAX_ITEMS_COUNT = 50

    @Inject lateinit var dataSource: DataSource
    @Inject lateinit var schedulers: RxSchedulers

    val posts = MutableLiveData<List<Post>>()
    val state = MutableLiveData<Int>()

    private val postsTemp = mutableListOf<Post>()
    private var disposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()

        disposable?.apply { if (!isDisposed) dispose() }
    }

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    fun loadPosts() {

        disposable?.apply { if (!isDisposed) dispose() }

        if (!isMaxItem()) {

            disposable = dataSource.getPosts(after(), LOUD_ITEMS_COUNT)
                    .observeOn(schedulers.main())
                    .doOnSubscribe { state.value = LOAD }
                    .doOnSuccess { postsTemp.addAll(it) }
                    .doOnSuccess { posts.value = postsTemp }
                    .subscribe (
                            { state.value = RESULT },
                            { state.value = ERROR })
        }
    }

    private fun isMaxItem() = itemCount() == MAX_ITEMS_COUNT

    private fun itemCount() = posts.value?.size ?: 0

    private fun after() = posts.value?.last()?.name ?: ""
}
