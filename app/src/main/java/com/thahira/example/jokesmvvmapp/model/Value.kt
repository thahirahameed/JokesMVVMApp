package com.thahira.example.jokesmvvmapp.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Value(
    @Json(name = "categories")
    val categories: List<String>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "joke")
    val joke: String
)