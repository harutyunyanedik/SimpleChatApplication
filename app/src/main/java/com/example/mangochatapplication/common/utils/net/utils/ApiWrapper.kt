package com.example.mangochatapplication.common.utils.net.utils

sealed class ApiWrapper<T> {
    data class Success<T>(val data: T?) : ApiWrapper<T>()

    data class Error<T>(val code: Int, val message: String?) : ApiWrapper<T>()

    data class UnknownError<T>(val message: String?) : ApiWrapper<T>()
}