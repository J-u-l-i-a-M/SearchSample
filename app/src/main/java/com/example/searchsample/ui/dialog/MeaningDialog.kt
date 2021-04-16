package com.example.searchsample.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.searchsample.R
import com.example.searchsample.databinding.DialogMeaningBinding
import com.example.searchsample.di.ApplicationLoader
import com.example.searchsample.di.application.DaggerAppComponent
import com.example.searchsample.entity.MeaningFull
import com.example.searchsample.presenters.MeaningPresenter
import com.example.searchsample.ui.activity.SearchActivity
import com.example.searchsample.util.loadTryUrl
import com.example.searchsample.views.BaseMoxyView
import com.example.searchsample.views.WordView
import moxy.MvpAppCompatDialogFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class MeaningDialog : MvpAppCompatDialogFragment(), BaseMoxyView, WordView {

    lateinit var binding: DialogMeaningBinding

    @Inject
    lateinit var presenterProvider: Provider<MeaningPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private val wordId: Int by lazy { arguments?.getInt(WORD_ID) ?: 0 }

    var dismissListener: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAppComponent
            .builder()
            .appModule(ApplicationLoader.instance.applicationModule)
            .build().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogMeaningBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)
        builder.setPositiveButton(R.string.ok) { _, _ ->
            dismiss()
        }
        return builder.create().apply { setCanceledOnTouchOutside(true) }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadWord(wordId)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener()
    }

    override fun onDataLoaded(word: MeaningFull) {
        binding.errorText.isVisible = false
        binding.wordText.text = word.text
        binding.meaningInfo.text = word.translation.note
        binding.meaningText.text = word.translation.text
        binding.meaningInfo.isVisible = word.translation.note.isNotBlank()
        binding.image.loadTryUrl(word.previewUrl, object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress < 100 && binding.progressBar.visibility == ProgressBar.GONE) {
                    binding.progressBar.isVisible = true
                }
                binding.progressBar.progress = progress
                if (progress == 100) {
                    binding.progressBar.isVisible = false
                }
            }
        }
        )
    }

    override fun onError(message: String) {
        binding.errorText.isVisible = true
        (activity as? SearchActivity)?.onError(message)
    }

    companion object {
        const val WORD_ID = "WORD_ID"
    }
}