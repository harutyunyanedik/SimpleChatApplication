package com.example.chatapplication.common.utils.net

import com.example.chatapplication.common.utils.net.model.ApiError
import com.example.chatapplication.common.utils.net.model.FetchPolicy

abstract class BaseNetworkBoundsResource<RequestType, ResultType> {
    abstract fun isError(response: RequestType?, result: ResultType?): ApiError?

    abstract fun processResponse(response: RequestType?, result: ResultType?): ResultType?

    abstract fun processErrorResponse(response: RequestType?, result: ResultType?)

    abstract fun saveNetworkResult(result: ResultType?)

    abstract suspend fun fetchFromNetwork(): RequestType?

    abstract fun fetchFromStorage(): ResultType?

    abstract fun determineFetchPolicy(): FetchPolicy
}