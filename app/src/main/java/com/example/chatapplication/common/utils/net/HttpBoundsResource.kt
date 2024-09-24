package com.example.chatapplication.common.utils.net

import com.example.chatapplication.common.utils.net.model.ApiError
import com.example.chatapplication.common.utils.net.model.Recourse
import com.example.chatapplication.common.utils.net.model.ApiWrapper
import com.example.chatapplication.common.utils.net.model.FetchPolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class HttpBoundsResource<RequestType, ResultType> : BaseNetworkBoundsResource<RequestType, ResultType>() {

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

    override fun determineFetchPolicy(): FetchPolicy = FetchPolicy.NETWORK_AND_CACHE

    operator fun invoke() = flow {
        emit(Recourse.Loading())
        val result: Recourse<ResultType>
        val fetchPolicy = determineFetchPolicy()

        when (fetchPolicy) {
            FetchPolicy.CACHE_ONLY -> {
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

            FetchPolicy.NETWORK_AND_CACHE -> {
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