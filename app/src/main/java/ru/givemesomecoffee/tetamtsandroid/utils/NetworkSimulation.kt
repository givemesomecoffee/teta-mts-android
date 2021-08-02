package ru.givemesomecoffee.tetamtsandroid.utils

import java.lang.Exception

fun simulateNetwork() {
    val listForErrorTest = listOf(200, 200, 500, 200)
    val serverAnswer = listForErrorTest.random()
    Thread.sleep(200L)
    if (serverAnswer == 500) {
       //throw Exception("Ошибка. Попробуйте обновить страницу")
    }
}