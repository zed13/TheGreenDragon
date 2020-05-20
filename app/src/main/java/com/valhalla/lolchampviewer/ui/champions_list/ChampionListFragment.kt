package com.valhalla.lolchampviewer.ui.champions_list

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.ui.champion_details_fragment.ChampionDetailsFragment
import com.valhalla.lolchampviewer.ui.core.BaseFragment
import com.valhalla.lolchampviewer.ui.core.bindView
import org.koin.android.viewmodel.ext.android.viewModel

class ChampionListFragment : BaseFragment(R.layout.fragment_champions_list) {

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
        lifecycle.addObserver(vm)
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
                    context!!,
                    "Couldn't load info about ${event.champion.name}." +
                            " Check connection and try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is ChampionListEvent.OpenChampion -> {
                activity?.supportFragmentManager
                    ?.beginTransaction()?.apply {
                        add(R.id.container, ChampionDetailsFragment(), "details")
                        addToBackStack(null)
                        commit()
                    }
            }
        }
    }
}