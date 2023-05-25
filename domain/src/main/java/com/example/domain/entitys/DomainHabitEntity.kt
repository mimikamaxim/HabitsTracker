package com.example.domain.entitys

import java.time.LocalDateTime

data class DomainHabitEntity(
    var name: String,//**
    var description: String,//**
    var priority: Int,//**
    var isGood: Boolean,//**
    var color: Int,//inop*
    var frequencyOfAllowedExecutions: Int,//**
    var periodInDays: Int,//**
    var doneDates: MutableList<LocalDateTime>,//auto*
    var initialDate: LocalDateTime,//auto
    var totalCompleteTimes: Int,//*
    var currentCompleteTimes: Int,//auto
    var uid: String = "",
    private val id: Int = -1
) {
    fun getID(): Int {
        if (id == -1) throw Exception("id of habit was not set correctly")
        return id
    }

    companion object {
        const val NoId = -2
    }
}