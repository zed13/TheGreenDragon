package com.valhalla.lolchampviewer.ui.champion_search

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.ui.core.BaseFragment
import com.valhalla.lolchampviewer.ui.core.bindView
import com.valhalla.lolchampviewer.ui.core.onClick
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ChampionSearchFragment : BaseFragment(R.layout.fragment_champion_search) {

    private val queryFieldView: EditText? by bindView(R.id.query_field)
    private val listView: RecyclerView? by bindView(R.id.list)
    private val messageContainerView: View? by bindView(R.id.message_container)
    private val messageView: TextView? by bindView(R.id.message)

    private val vm: ChampionSearchViewModel by viewModel()

    private val searchAdapter: ChampionSearchAdapter = ChampionSearchAdapter {
        vm.onChampionClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setOnTouchListener { _, _ -> true }

        queryFieldView?.addTextChangedListener {
            vm.onQueryChanged(it?.toString() ?: "")
        }

        onClick(R.id.clear_button) {
            vm.onClearQueryClick()
        }

        listView?.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = searchAdapter
        }

        vm.list bindTo ::setList
        vm.championSearchEvents bindTo ::handleEvent
        vm.queryText bindTo ::setQuery
    }

    private fun setList(result: ChampionSearchResult) {
        when (result) {
            ChampionSearchResult.NoQuery -> {
                listView?.isVisible = false
                messageContainerView?.isVisible = true
                messageView?.text = null
            }
            is ChampionSearchResult.NotFound -> {
                listView?.isVisible = false
                messageContainerView?.isVisible = true
                messageView?.text = "Nothing found by the query '${result.query}'"
            }
            is ChampionSearchResult.Filtered -> {
                listView?.isVisible = true
                searchAdapter.items = result.list
                messageContainerView?.isVisible = false
            }
        }
    }

    private fun setQuery(query: String) {
        if (query != queryFieldView?.text?.toString()) {
            queryFieldView?.setText(query)
        }
    }

    private fun handleEvent(event: ChampionSearchEvent) {
        if (event is ChampionSearchEvent.OpenChampion) {
            findNavController().navigate(
                R.id.action_championSearch_to_championDetails,
                bundleOf("champion" to event.champion)
            )
        }
    }
}