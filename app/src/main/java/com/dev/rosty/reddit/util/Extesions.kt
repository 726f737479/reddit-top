package com.dev.rosty.reddit.util

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.File
import java.lang.Exception

inline fun Fragment.checkSelfPermission(permission: String, action: () -> Unit, requestCode: Int) {

    context?.apply {

        val check = ContextCompat.checkSelfPermission(this, permission)

        if (check != PackageManager.PERMISSION_GRANTED) {

            activity?.apply {

                ActivityCompat.requestPermissions(this,
                        arrayOf(permission), requestCode)
            }

        } else action.invoke()
    }
}

fun Fragment.updateGallery(path: String) {

    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    intent.data = Uri.fromFile(File(path))

    context?.apply {  sendBroadcast(intent) }
}

inline fun Picasso.loadBitmap(url: String,
                              crossinline load: () -> Unit, crossinline error: () -> Unit,
                              crossinline success: () -> Unit, crossinline bitmap: (Bitmap) -> Unit) {

    load(url).into(object : Target {
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            load.invoke()
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            error.invoke()
        }

        override fun onBitmapLoaded(image: Bitmap?, from: Picasso.LoadedFrom?) {
            success.invoke()
            image?.apply {  bitmap.invoke(this) }
        }
    })
}
