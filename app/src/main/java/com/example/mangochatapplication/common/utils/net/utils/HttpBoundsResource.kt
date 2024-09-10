package com.example.mangochatapplication.common.utils.net.utils

import com.example.mangochatapplication.common.utils.net.model.ApiError
import com.example.mangochatapplication.common.utils.FetchPolicy
import com.example.mangochatapplication.common.utils.Recourse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class HttpBoundsResource<RequestType, ResultType> : BaseNetworkBoundsResource<RequestType, ResultType>() {

    abstract suspend fun fetchFromNetwork(): RequestType?

    override fun isError(response: RequestType?, result: ResultType?): ApiError? {
        return when (response) {
            is ApiWrapper.Error<*> -> ApiError(code = response.code, message = response.message)
            is ApiWrapper.UnknownError<*> -> ApiError(message = response.message)
            else -> null
        }
    }

    override fun processErrorResponse(response: RequestType?, result: ResultType?) {}

    override fun saveNetworkResult(result: ResultType?) {}

    override fun fetchFromStorage(): ResultType? = null

    override fun determineFetchPolicy(): FetchPolicy = FetchPolicy.NETWORK_AND_CASH

    operator fun invoke() = flow {
        emit(Recourse.Loading())
        val result: Recourse<ResultType>
        val fetchPolicy = determineFetchPolicy()

        when (fetchPolicy) {
            FetchPolicy.CASH_ONLY -> {
                val storageValue = fetchFromStorage()
                result = Recourse.Success(storageValue)
            }

            FetchPolicy.NETWORK_ONLY -> {
                val apiResponse = fetchFromNetwork()
                val error = isError(apiResponse, null)
                if (error == null) {
                    val processedResponse = processResponse(apiResponse, null)
                    result = Recourse.Success(processedResponse)
                } else {
                    processErrorResponse(apiResponse, null)
                    result = Recourse.Error(error.message)
                }
            }

            FetchPolicy.NETWORK_AND_CASH -> {
                val storageValue = fetchFromStorage()
                val apiResponse = fetchFromNetwork()
                val error = isError(apiResponse, null)
                if (error == null) {
                    val processedResponse = processResponse(apiResponse, null)
                    saveNetworkResult(processedResponse)
                    result = Recourse.Success(processedResponse)
                } else {
                    processErrorResponse(apiResponse, null)
                    result = Recourse.Error(error.message, storageValue)
                }
            }
        }

        emit(result)
    }.flowOn(Dispatchers.IO)
}