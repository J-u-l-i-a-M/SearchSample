package com.example.searchsample.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(windowToken, 0)
}

fun WebView.loadTryUrl(url: String, webChrome: WebChromeClient) {
    isVerticalScrollBarEnabled = false
    isHorizontalScrollBarEnabled = false
    webViewClient = WebViewClient()
    webChromeClient = webChrome
    loadUrl(url)
}
