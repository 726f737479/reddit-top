package com.dev.rosty.reddit.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.dev.rosty.reddit.R
import com.dev.rosty.reddit.presentation.posts.PostsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, PostsFragment())
                    .commit()
        }
    }
}
