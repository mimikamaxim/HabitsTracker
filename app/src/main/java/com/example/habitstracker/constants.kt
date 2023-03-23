package com.example.habitstracker

import android.util.Log

private const val isDevelop = true

fun devDoSomeStuff(lambda: Runnable) {
    if (isDevelop)
        lambda.run()
}

const val TAG = "my_TAG " //tag for myLogger and Lig invocation
private var invocationCounter = 0

fun myLogger(s: String) {
    Log.i(TAG + invocationCounter, s)
    invocationCounter++
}