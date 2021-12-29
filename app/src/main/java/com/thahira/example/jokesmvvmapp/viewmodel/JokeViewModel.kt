package com.thahira.example.jokesmvvmapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thahira.example.jokesmvvmapp.rest.JokeApi
import com.thahira.example.jokesmvvmapp.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class JokeViewModel(
    private val jokeApi: JokeApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val coroutineScope: CoroutineScope = CoroutineScope(ioDispatcher)
): ViewModel(){

    private var _allJokes: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING())
    val allJokesObserver : LiveData<UIState> get() = _allJokes

    private var _randomJoke: MutableLiveData<UIState> = MutableLiveData(null)
    val randomJokeObserver : LiveData<UIState> get() = _randomJoke

    private var _heroJoke: MutableLiveData<UIState> = MutableLiveData(null)
    val heroJokeObserver : LiveData<UIState> get() = _heroJoke

    fun getRandomJoke(){
        coroutineScope.launch {
            try {
                val response = jokeApi.getRandomJoke()
                response.body()?.let{   jokes->
                    _randomJoke.postValue(UIState.SINGLE_SUCCESS(jokes))
                }?: _randomJoke.postValue(UIState.ERROR(Throwable("Response is null")))
            }catch(e: Exception){
                _randomJoke.postValue(UIState.ERROR(e))
            }
        }
    }


    fun getJokes(){
        coroutineScope.launch {
            try{
                val response = jokeApi.getJokes()
                response.body()?.let { jokes->
                    _allJokes.postValue(UIState.SUCCESS(listOf(jokes)))
                }?: _allJokes.postValue(UIState.ERROR(Throwable("Response is null")))
            }catch (e: Exception){
                _allJokes.postValue((UIState.ERROR(e)))
            }
        }
    }


    fun getHeroJoke(first: String, last: String){
        coroutineScope.launch{
            try{
                val response = jokeApi.getRandomJoke(first,last)
                response.body()?.let { herojoke->
                    _heroJoke.postValue(UIState.SINGLE_SUCCESS(herojoke))
                }?: _heroJoke.postValue((UIState.ERROR(Throwable("Response is null"))))
            }catch(e:Exception){
                _heroJoke.postValue(UIState.ERROR(e))
            }
        }
    }
}