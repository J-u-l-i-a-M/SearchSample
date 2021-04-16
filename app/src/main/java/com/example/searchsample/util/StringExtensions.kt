package com.example.searchsample.util


fun String.convertToUrl(): String = if (this.startsWith(httpPrefix)) "" else httpPrefix + this

private const val httpPrefix = "http://"

