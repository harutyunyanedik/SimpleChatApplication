package com.example.mangochatapplication.common.utils.net.utils

import com.example.mangochatapplication.common.utils.net.model.ApiError
import com.example.mangochatapplication.common.utils.FetchPolicy

abstract class BaseNetworkBoundsResource<RequestType, ResultType> {
    abstract fun isError(response: RequestType?, result: ResultType?): ApiError?

    abstract fun processResponse(response: RequestType?, result: ResultType?): ResultType?

    abstract fun processErrorResponse(response: RequestType?, result: ResultType?)

    abstract fun saveNetworkResult(result: ResultType?)

    abstract fun fetchFromStorage(): ResultType?

    abstract fun determineFetchPolicy(): FetchPolicy
}