package com.example.domain

sealed class AddDoneResult

class DoMore(val reminder: Int) : AddDoneResult()

class CanDoMore(val reminder: Int) : AddDoneResult()

object YouDone : AddDoneResult()

object StopDoingIt : AddDoneResult()
