package com.example.core.utils

sealed class Resource<T>(val data: T? = null, val messageId: Int? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(messageId: Int, data: T? = null) : Resource<T>(data, messageId)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}