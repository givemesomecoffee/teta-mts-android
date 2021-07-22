package ru.givemesomecoffee.tetamtsandroid.utils

fun simulateNetwork(): Int {
    val listForErrorTest = listOf(200, 200, 500, 200)
    val serverAnswer = listForErrorTest.random()
    Thread.sleep(200L)
    return serverAnswer
}