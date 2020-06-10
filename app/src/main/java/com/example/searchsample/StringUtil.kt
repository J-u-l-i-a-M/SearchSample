package com.example.searchsample

object StringUtil {
    fun getTryUrl(url: String) =
        if (url.startsWith(httpPrefix)) "" else httpPrefix + url

    private const val httpPrefix = "http:/"
}
