package com.example.chatapplication.common.utils.net.model

enum class RecourseStatusEnum {
    SUCCESS, ERROR, LOADING
}

sealed class Recourse<T>(
    statusEnum: RecourseStatusEnum,
    error: String? = null,
    data: T? = null
) {
    data class Success<T>(val data: T?) : Recourse<T>(statusEnum = RecourseStatusEnum.SUCCESS, data = data, error = null)

    data class Error<T>(val error: String?, val data: T? = null) : Recourse<T>(statusEnum = RecourseStatusEnum.ERROR, error = error, data = data)

    class Loading<T> : Recourse<T>(statusEnum = RecourseStatusEnum.LOADING, error = null, data = null)
}