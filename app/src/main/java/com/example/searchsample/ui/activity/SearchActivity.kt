package com.example.searchsample.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.searchsample.R
import com.example.searchsample.databinding.ActivitySearchBinding
import com.example.searchsample.di.ApplicationLoader
import com.example.searchsample.di.application.DaggerAppComponent
import com.example.searchsample.entity.Word
import com.example.searchsample.presenters.SearchPresenter
import com.example.searchsample.ui.adapters.WordMeansAdapter
import com.example.searchsample.ui.dialog.MeaningDialog
import com.example.searchsample.util.hideKeyboard
import com.example.searchsample.views.SearchView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class SearchActivity : MvpAppCompatActivity(), SearchView {

    @Inject
    lateinit var presenterProvider: Provider<SearchPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    init {
        DaggerAppComponent
            .builder()
            .appModule(ApplicationLoader.instance.applicationModule)
            .build().inject(this)
    }

    private val adapter: WordMeansAdapter by lazy {
        WordMeansAdapter()
        {
            presenter.showMeaning(it.id)
        }
    }
    private var dialog: MeaningDialog? = null

    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.makeSearch.setOnClickListener {
            presenter.searchWord(binding.searchWord.text.toString())
        }
        binding.recyclerView.adapter = adapter
        binding.searchWord.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                binding.makeSearch.isEnabled = editable.toString().isNotBlank()
            }

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
        })
    }

    override fun onDataLoaded(words: List<Word>) {
        binding.searchWord.hideKeyboard()
        adapter.updateWords(words)
        binding.emptyText.isVisible = words.isEmpty()
        binding.recyclerView.isVisible = words.isNotEmpty()
    }

    override fun onSearchLoaded(search: String) {
        binding.searchWord.setText(search)
    }

    override fun showWaitDialog(show: Boolean) {
        binding.progressBar.isVisible = show
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