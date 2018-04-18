package com.dev.rosty.reddit.presentation.image

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.dev.rosty.reddit.common.FileManager

import com.dev.rosty.reddit.common.RxSchedulers
import com.dev.rosty.reddit.data.DataSource
import com.dev.rosty.reddit.injection.AppComponent
import com.dev.rosty.reddit.presentation.ERROR
import com.dev.rosty.reddit.presentation.LOAD
import com.dev.rosty.reddit.presentation.RESULT
import com.squareup.picasso.Picasso

import javax.inject.Inject

import io.reactivex.disposables.Disposable

class ImageViewModel : ViewModel(), AppComponent.Injectable {

    @Inject lateinit var fileManager: FileManager

    val state = MutableLiveData<Int>()

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    fun saveImage(bitmap: Bitmap, action: (String) -> Unit) {

        fileManager.saveImage(bitmap)?.apply { action.invoke(this) }
    }
}
