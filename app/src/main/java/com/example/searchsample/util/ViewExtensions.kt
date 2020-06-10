package com.example.searchsample.util

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.dialog_meaning.*

fun View.visibility(show: Boolean) {
    val newState = if (show) VISIBLE else GONE
    val oldState = this.visibility
    if (newState != oldState) this.visibility = newState
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun WebView.loadTryUrl(url: String, webChrome: WebChromeClient) {
    isVerticalScrollBarEnabled = false
    isHorizontalScrollBarEnabled = false
    webViewClient = WebViewClient()
    webChromeClient = webChrome
    loadUrl(url)
}
