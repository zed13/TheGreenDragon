package com.valhalla.lolchampviewer.ui.champion_search

import android.util.Log
import androidx.lifecycle.*
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import com.valhalla.lolchampviewer.ui.Wizard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ChampionSearchViewModel(
    private val championsRepository: ChampionsRepository,
    private val wizard: Wizard
) : ViewModel(), LifecycleObserver {

    private val _list: MutableStateFlow<List<ChampionShort>> =
        MutableStateFlow(emptyList())

    private val _query: MutableStateFlow<String> = MutableStateFlow("")

    private val mainScope: CoroutineScope = MainScope()

    val list: Flow<ChampionSearchResult>
        get() = _list.combine(_query) { list, query ->
            if (query.isBlank()) {
                ChampionSearchResult.NoQuery
            } else {
                list.asSequence()
                    .filter {
                        it.name.contains(query, ignoreCase = true)
                                || it.title.contains(query, ignoreCase = true)
                    }.map { ChampionSearchViewData(it, query) }
                    .toList()
                    .let {
                        if (it.isEmpty()) {
                            ChampionSearchResult.NotFound(query)
                        } else {
                            ChampionSearchResult.Filtered(it)
                        }
                    }
            }
        }

    val query: Flow<String>
        get() = _query

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onCreate() {
        viewModelScope.launch {
            _list.value = championsRepository.getChampions()
        }
    }

    fun onQueryChanged(query: String) {
        _query.value = query
    }

    fun onChampionClicked(data: ChampionSearchViewData) {
        viewModelScope.launch {
            val champion: Champion = try {
                championsRepository.getChampion(data.champion.id)
            } catch (ex: Exception) {
                Log.e("ChampionSearch", "Couldn't load champion ${data.champion.name}", ex)
                null
            } ?: return@launch

            mainScope.launch { wizard.openChampionDetails(champion) }
        }
    }

    fun onClearQueryClick() {
        _query.value = ""
    }
}