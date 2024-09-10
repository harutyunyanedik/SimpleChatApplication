package com.example.mangochatapplication.presentation.di

import com.example.mangochatapplication.common.utils.net.MangoChatWebService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<MangoChatWebService> {
        createWebService<MangoChatWebService>("https://plannerok.ru/")
    }
}


@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> createWebService(baseUrl: String): T {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    return retrofit.create(T::class.java)
}