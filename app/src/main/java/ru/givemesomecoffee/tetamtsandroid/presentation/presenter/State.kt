package ru.givemesomecoffee.tetamtsandroid.presentation.presenter

sealed class State<out T> {
    object LoadingState : State<Nothing>()
    data class DataState<T>(var data: T) : State<T>()
}