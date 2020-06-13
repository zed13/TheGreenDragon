package com.valhalla.lolchampviewer.ui.champion_search

import android.util.Log
import androidx.lifecycle.*
import androidx.test.espresso.idling.CountingIdlingResource
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ChampionSearchViewModel(
    private val championsRepository: ChampionsRepository
) : ViewModel(), LifecycleObserver {

    private val _list: MutableStateFlow<List<ChampionShort>> =
        MutableStateFlow(emptyList())

    private val _query: MutableStateFlow<String> = MutableStateFlow("")

    private val _events: BroadcastChannel<ChampionSearchEvent> = BroadcastChannel(1)

    val events: Flow<ChampionSearchEvent>
        get() = _events.openSubscription().receiveAsFlow()

    internal val searchResource: CountingIdlingResource =
        CountingIdlingResource("champion-search", true)

    val list: Flow<ChampionSearchResult>
        get() = _list.combine(_query) { l, q -> l to q }
            .onEach { searchResource.increment() }
            .map { (list, query) ->
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
            }.onEach { searchResource.decrement() }

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

            _events.send(ChampionSearchEvent.OpenChampion(champion))
        }
    }

    fun onClearQueryClick() {
        _query.value = ""
    }
}

sealed class ChampionSearchEvent {
    data class OpenChampion(val champion: Champion) : ChampionSearchEvent()
}