package com.thahira.example.jokesmvvmapp.utils

import com.thahira.example.jokesmvvmapp.model.Jokes
import com.thahira.example.jokesmvvmapp.model.SingleJoke

sealed class UIState{
    class LOADING(val isLoading : Boolean = true) : UIState()
    class SUCCESS(val jokes: List<Jokes>) : UIState()
    class SINGLE_SUCCESS(val joke : SingleJoke) : UIState()
    class ERROR(val error: Throwable) : UIState()
}
