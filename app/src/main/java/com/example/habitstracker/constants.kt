package com.example.habitstracker

const val KEY_ID = "key_id"
const val TAG = "my_TAG"


private const val t = true
private const val f = false
private const val isDevelop = t

fun devDoSomeStuff (lambda: Runnable) {
    if (isDevelop)
        lambda.run()
}