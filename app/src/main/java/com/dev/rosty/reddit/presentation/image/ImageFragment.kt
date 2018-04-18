package com.dev.rosty.reddit.presentation.image


import android.Manifest
import android.arch.lifecycle.Observer
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.dev.rosty.reddit.R
import com.dev.rosty.reddit.presentation.BaseFragment
import com.dev.rosty.reddit.presentation.ERROR
import com.dev.rosty.reddit.presentation.LOAD
import com.dev.rosty.reddit.presentation.RESULT
import com.dev.rosty.reddit.util.checkSelfPermission
import com.dev.rosty.reddit.util.loadBitmap
import com.dev.rosty.reddit.util.updateGallery
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.android.synthetic.main.layout_state.*


class ImageFragment : BaseFragment<ImageViewModel>() {

    private var url: String? = null
    private var bitmap: Bitmap? = null

    override fun getLayoutRes() = R.layout.fragment_image

    override fun getViewModelClass() = ImageViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply { url = this.getString(ARG_URL)?.apply { loadImage(this) }}

        close.setOnClickListener { activity?.onBackPressed() }
        save.setOnClickListener { bitmap?.apply { saveImage(this) }}
        btnRetry.setOnClickListener { url?.apply { loadImage(this) } }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.state.observe(this, Observer<Int> { setupState(it) })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {

            PERMISSION_REQUEST -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    bitmap?.apply { viewModel.saveImage(this, {

                        showSnack()
                        updateGallery(it)
                    })}
                }
            }
        }
    }

    private fun setupState(state: Int?) {

        stateError.visibility = if (state == ERROR) View.VISIBLE else View.GONE
        stateProgress.visibility = if (state == LOAD) View.VISIBLE else View.GONE
        save.visibility = if (state == RESULT) View.VISIBLE else View.GONE
    }

    private fun saveImage(bitmap: Bitmap) {

        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                { viewModel.saveImage(bitmap, {

                    showSnack()
                    updateGallery(it)

                })}, PERMISSION_REQUEST)
    }

    private fun loadImage(url: String){

        Picasso.get().loadBitmap(url,
                { viewModel.state.value = LOAD },
                { viewModel.state.value = ERROR },
                { viewModel.state.value = RESULT } ,
                { bitmap = it.apply { image.setImageBitmap(it) } })
    }

    private fun showSnack() {
        Snackbar.make(view!!, R.string.text_saved_gallery, Snackbar.LENGTH_SHORT).show()
    }

    companion object {

        private const val PERMISSION_REQUEST = 1
        private const val ARG_URL = "image_url"

        @JvmStatic
        fun newInstance(url: String) = ImageFragment()
                .apply { arguments = Bundle().apply { putString(ARG_URL, url) } }
    }
}
