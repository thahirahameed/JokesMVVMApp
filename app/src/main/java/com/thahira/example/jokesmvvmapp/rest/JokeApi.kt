package com.thahira.example.jokesmvvmapp.rest

import com.thahira.example.jokesmvvmapp.model.Jokes
import com.thahira.example.jokesmvvmapp.model.SingleJoke
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApi {

    @GET(JOKES+ JOKES_COUNT_20)
    suspend fun getJokes(
        @Query("firstName") first: String? = null,
        @Query("lastName") last: String? = null
    ): Response<Jokes>

    @GET(JOKES)
    suspend fun getRandomJoke(
        @Query("firstName") first : String? = null,
        @Query("lastName") last : String? = null
    ): Response<SingleJoke>


    companion object{

        const val BASE_URL = "https://api.icndb.com/"

        private const val JOKES ="jokes/random/"
        private const val JOKES_COUNT_20 = "20"
    }
}