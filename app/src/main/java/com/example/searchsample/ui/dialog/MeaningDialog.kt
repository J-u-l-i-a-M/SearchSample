package com.example.searchsample.ui.dialog

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import com.example.searchsample.R
import com.example.searchsample.di.ApplicationLoader
import com.example.searchsample.di.DaggerAppComponent
import com.example.searchsample.entity.MeaningFull
import com.example.searchsample.presenters.MeaningPresenter
import com.example.searchsample.ui.activity.SearchActivity
import com.example.searchsample.util.loadTryUrl
import com.example.searchsample.util.visibility
import com.example.searchsample.views.WordView
import kotlinx.android.synthetic.main.dialog_meaning.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class MeaningDialog : BaseDialog(), WordView {

    override fun layoutResId() = R.layout.dialog_meaning

    override fun positiveButton() = R.string.ok

    override fun positiveClick() {
        super.positiveClick()
        dismiss()
        dismissListener.invoke()
    }

    @Inject
    lateinit var presenterLazy: dagger.Lazy<MeaningPresenter>

    @InjectPresenter
    lateinit var presenter: MeaningPresenter

    @ProvidePresenter
    fun provide(): MeaningPresenter = presenterLazy.get()

    private val wordId: Int by lazy { arguments?.getInt(WORD_ID) ?: 0 }

    override fun inject() {
        DaggerAppComponent
            .builder()
            .applicationModule(ApplicationLoader.instance.applicationModule)
            .build().inject(this)
    }

    override fun initViews() {
        super.initViews()
        presenter.loadWord(wordId)
    }

    override fun onDataLoaded(word: MeaningFull) {
        error_text.visibility(false)
        word_text.text = word.text
        meaning_info.text = word.translation.note
        meaning_text.text = word.translation.text
        meaning_info.visibility(word.translation.note.isNotBlank())
        image.loadTryUrl(word.previewUrl, object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress < 100 && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility(true)
                }
                progressBar.progress = progress
                if (progress == 100) {
                    progressBar.visibility(false)
                }
            }
        }
        )
    }

    override fun onError(message: String) {
        error_text.visibility(true)
        (activity as? SearchActivity)?.onError(message)
    }

    companion object {
        const val WORD_ID = "WORD_ID"
    }
}