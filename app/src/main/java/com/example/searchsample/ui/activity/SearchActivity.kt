package com.example.searchsample.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.searchsample.di.ApplicationLoader
import com.example.searchsample.presenters.SearchPresenter
import com.example.searchsample.R
import com.example.searchsample.di.DaggerAppComponent
import com.example.searchsample.entity.Word
import com.example.searchsample.ui.adapters.WordMeansAdapter
import com.example.searchsample.ui.dialog.MeaningDialog
import com.example.searchsample.util.hideKeyboard
import com.example.searchsample.util.visibility
import com.example.searchsample.views.SearchView
import kotlinx.android.synthetic.main.activity_search.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class SearchActivity : MvpAppCompatActivity(), SearchView {

    @Inject
    lateinit var presenterLazy: dagger.Lazy<SearchPresenter>

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    @ProvidePresenter
    fun provide(): SearchPresenter = presenterLazy.get()

    init {
        DaggerAppComponent
            .builder()
            .applicationModule(ApplicationLoader.instance.applicationModule)
            .build().inject(this)
    }

    private val adapter: WordMeansAdapter by lazy {
        WordMeansAdapter()
        {
            presenter.showMeaning(it.id)
        }
    }
    private var dialog: MeaningDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        make_search.setOnClickListener {
            presenter.searchWord(search_word.text.toString())
        }
        recycler_view.adapter = adapter
        search_word.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                make_search.isEnabled = editable.toString().isNotBlank()
            }

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
        })
    }

    override fun onDataLoaded(words: List<Word>) {
        search_word.hideKeyboard()
        adapter.updateWords(words)
        empty_text.visibility(words.isEmpty())
        recycler_view.visibility(words.isNotEmpty())
    }

    override fun onSearchLoaded(search: String) {
        search_word.setText(search)
    }

    override fun showWaitDialog(show: Boolean) {
        progressBar.visibility(show)
    }

    override fun showMeaning(meaningId: Int) {
        dialog = MeaningDialog().apply {
            arguments = Bundle().apply {
                putInt(MeaningDialog.WORD_ID, meaningId)
                dismissListener = { this@SearchActivity.presenter.hide() }
            }
        }
        dialog?.show(supportFragmentManager, "MeaningDialog")
    }

    override fun hideDialog() {
        dialog?.dismiss()
    }

    override fun onError(message: String) {
        val error = if (message.isBlank()) getString(R.string.unknown_error) else message
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        adapter.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        adapter.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}