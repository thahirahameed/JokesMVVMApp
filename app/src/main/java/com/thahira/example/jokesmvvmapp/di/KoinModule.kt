package com.thahira.example.jokesmvvmapp.di

import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.thahira.example.jokesmvvmapp.adapter.JokesRecyclerViewAdapter
import com.thahira.example.jokesmvvmapp.rest.JokeApi
import com.thahira.example.jokesmvvmapp.view.FirstFragment
import com.thahira.example.jokesmvvmapp.view.HeroFragment
import com.thahira.example.jokesmvvmapp.view.JokesFragment
import com.thahira.example.jokesmvvmapp.viewmodel.JokeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val networkModule = module{

    fun provideMoshi() = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()

    fun provideNetworkApi(okHttpClient: OkHttpClient, moshi: Moshi) =
        Retrofit.Builder()
            .baseUrl(JokeApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(JokeApi::class.java)

    single { provideMoshi() }
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideNetworkApi(get(),get()) }
}

val appModule = module {
    single { JokesRecyclerViewAdapter() }
    single { FirstFragment() }
    single { JokesFragment() }
    single { HeroFragment() }
}

val viewModelModule = module{
    viewModel{
        JokeViewModel(get())
    }
}