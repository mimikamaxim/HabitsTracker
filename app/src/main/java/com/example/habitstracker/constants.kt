package com.example.habitstracker

import android.util.Log

const val KEY_ID = "key_id"
const val TAG = "my_TAG "


private const val t = true
private const val f = false
private const val isDevelop = t
private var invocationCounter = 0

fun devDoSomeStuff(lambda: Runnable) {
    if (isDevelop)
        lambda.run()
}

fun myLogger(s: String) {
    Log.i(TAG + invocationCounter, s)
    invocationCounter++
}