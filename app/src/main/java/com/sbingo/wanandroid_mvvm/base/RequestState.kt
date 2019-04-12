package com.sbingo.wanandroid_mvvm.base

/**
 * Author: Sbingo666
 * Date:   2019/4/12
 */

enum class Status {
    LOADING,
    SUCCESS,
    ERROR,
}

data class RequestState<out T>(val status: Status, val data: T?, val message: String? = null) {
    companion object {
        fun <T> loading(data: T? = null) =
            RequestState(Status.LOADING, data)

        fun <T> success(data: T? = null) =
            RequestState(Status.SUCCESS, data)

        fun <T> error(msg: String? = null, data: T? = null) =
            RequestState(Status.ERROR, data, msg)
    }

    fun isLoading() :Boolean= status == Status.LOADING
    fun isSuccess() :Boolean= status == Status.SUCCESS
    fun isError() :Boolean= status == Status.ERROR
}
