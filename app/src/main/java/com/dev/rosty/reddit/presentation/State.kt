package com.dev.rosty.reddit.presentation

import android.support.annotation.IntDef

const val IDLE = 0
const val LOAD = 1
const val ERROR = 2
const val RESULT = 3

@IntDef(IDLE, LOAD, ERROR, RESULT)
@Retention(AnnotationRetention.SOURCE)
annotation class State