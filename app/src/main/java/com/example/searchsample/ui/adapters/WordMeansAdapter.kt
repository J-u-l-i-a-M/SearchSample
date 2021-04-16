package com.example.searchsample.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.example.searchsample.R
import com.example.searchsample.databinding.ViewItemChildMeaningBinding
import com.example.searchsample.databinding.ViewItemParentWordBinding
import com.example.searchsample.entity.Meaning
import com.example.searchsample.entity.Word
import kotlinx.android.extensions.LayoutContainer

class WordMeansAdapter(
    private val itemClick: (Meaning) -> Unit
): ExpandableRecyclerAdapter<Word, Meaning, WordMeansAdapter.PVH, WordMeansAdapter.CVH>(emptyList()) {

    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): PVH =
        PVH(LayoutInflater.from(parentViewGroup.context).inflate(R.layout.view_item_parent_word, parentViewGroup, false))

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): CVH =
        CVH(LayoutInflater.from(childViewGroup.context).inflate(R.layout.view_item_child_meaning, childViewGroup, false))

    override fun onBindParentViewHolder(parentViewHolder: PVH, parentPosition: Int, parent: Word) {
        parentViewHolder.bind(parent.text)
    }

    override fun onBindChildViewHolder(childViewHolder: CVH, parentPosition: Int, childPosition: Int, child: Meaning) {
        childViewHolder.bind(child)
    }

    fun updateWords(words: List<Word>) {
        setParentList(words, true)
    }

    inner class PVH(override val containerView: View) : ParentViewHolder<Word, Meaning>(containerView),
        LayoutContainer {

        fun bind(wordText: String) {
            ViewItemParentWordBinding.bind(containerView).wordText.text = wordText
        }
    }

    inner class CVH(override val containerView: View) : ChildViewHolder<Meaning>(containerView),
        LayoutContainer {
        fun bind(meaning: Meaning) {
            itemView.setOnClickListener { itemClick.invoke(meaning) }
            ViewItemChildMeaningBinding.bind(containerView).meaningText.text = meaning.translation.text
        }
    }
}
