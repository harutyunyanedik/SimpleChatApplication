package com.example.chatapplication.common.utils.net

import com.example.chatapplication.common.utils.net.model.ApiWrapper
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import retrofit2.Response
import kotlin.reflect.KClass

suspend inline fun <reified T> parseResponse(crossinline function: suspend () -> Response<T>): ApiWrapper<T> {
    return try {
        val apiResponse = function.invoke()
        val statusCode = apiResponse.code()
        val body = apiResponse.body()
        val errorBody = apiResponse.errorBody()
        if (apiResponse.isSuccessful) {
            ApiWrapper.Success(data = body)
        } else {
            ApiWrapper.Error(code = statusCode, message = errorBody?.string())
        }
    } catch (e: Exception) {
        ApiWrapper.UnknownError(e.message)
    }
}

val json = Json {
    ignoreUnknownKeys = true
}

@OptIn(InternalSerializationApi::class)
fun <E : Any> parseErrorBody(errorBody: String, clazz: KClass<E>): E? {
    return try {
        json.decodeFromString(clazz.serializer(), errorBody)
    } catch (e: Exception) {
        null
    }
}