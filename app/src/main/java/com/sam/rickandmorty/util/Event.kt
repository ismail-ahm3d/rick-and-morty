package com.sam.rickandmorty.util

sealed class Event {
    class Success(val data: Any) : Event()
    class Failure(val errorText: String) : Event()
    object Loading : Event()
    object Empty : Event()
}