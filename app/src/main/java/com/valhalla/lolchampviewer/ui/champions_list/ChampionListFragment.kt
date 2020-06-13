package com.valhalla.lolchampviewer.ui.champions_list

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.ui.core.BaseFragment
import com.valhalla.lolchampviewer.ui.core.bindView
import com.valhalla.lolchampviewer.ui.core.onClick
import org.koin.android.viewmodel.ext.android.viewModel

class ChampionListFragment : BaseFragment(R.layout.fragment_champions_list) {

    private val searchBarView: View? by bindView(R.id.search_bar)
    private val listView: RecyclerView? by bindView(R.id.list)
    private val progressView: View? by bindView(R.id.progress)
    private val errorView: View? by bindView(R.id.error)
    private val errorLabelView: TextView? by bindView(R.id.error_label)
    private val actionProgress: View? by bindView(R.id.action_progress)
    private val refresherView: SwipeRefreshLayout? by bindView(R.id.refresher)

    private val adapter: ChampionsAdapter = ChampionsAdapter {
        vm.onItemClick(it)
    }

    private val vm: ChampionListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            vm.registerOn(lifecycle)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refresherView?.isEnabled = false
        refresherView?.setOnRefreshListener {
            vm.load()
        }

        listView?.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = this@ChampionListFragment.adapter
            addItemDecoration(
                DividerItemDecoration(
                    view.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        onClick(R.id.search_bar) {
            vm.onSearchClick()
        }
        onClick(R.id.filter_button) {
            Toast.makeText(view.context, "Filter button clicked", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        vm.listState bindTo ::setListState
        vm.list bindTo ::setItems
        vm.isItemLoading bindTo ::setItemLoading
        vm.championListEvents bindTo ::handleEvent
    }

    private fun setListState(state: ChampionListState) {
        actionProgress?.isVisible = false
        when (state) {
            ChampionListState.Empty -> {
                listView?.isVisible = false
                progressView?.isVisible = false
                errorView?.isVisible = true
                errorLabelView?.text = "The champion list is empty"
            }
            ChampionListState.Loading -> {
                listView?.isVisible = false
                progressView?.isVisible = true && refresherView?.isEnabled == false
                errorView?.isVisible = false
            }
            is ChampionListState.Error -> {
                listView?.isVisible = false
                progressView?.isVisible = false
                errorView?.isVisible = true
                errorLabelView?.text = state.error
            }
            ChampionListState.Ready -> {
                listView?.isVisible = true
                progressView?.isVisible = false
                errorView?.isVisible = false
            }
        }
        if (state != ChampionListState.Loading) {
            refresherView?.isEnabled = true
            refresherView?.isRefreshing = false
        }
    }

    private fun setItems(list: List<ChampionShort>) {
        adapter.items = list
    }

    private fun setItemLoading(isLoading: Boolean) {
        actionProgress?.isVisible = isLoading
    }

    private fun handleEvent(event: ChampionListEvent) {
        when (event) {
            is ChampionListEvent.ChampionLoadingFailed -> {
                Toast.makeText(
                    requireContext(),
                    "Couldn't load info about ${event.champion.name}." +
                            " Check connection and try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is ChampionListEvent.OpenChampion -> {
                findNavController().navigate(
                    R.id.action_championList_to_championDetails, bundleOf(
                        "champion" to event.champion
                    )
                )
            }
            is ChampionListEvent.OpenSearch -> {
                findNavController().navigate(R.id.action_championList_to_championSearch)
            }
        }
    }
}