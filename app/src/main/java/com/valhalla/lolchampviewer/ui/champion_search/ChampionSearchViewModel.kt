package com.valhalla.lolchampviewer.ui.champion_search

import android.util.Log
import androidx.lifecycle.*
import androidx.test.espresso.idling.CountingIdlingResource
import com.valhalla.lolchampviewer.core.SingleLiveEvent
import com.valhalla.lolchampviewer.core.combineLatest
import com.valhalla.lolchampviewer.core.perform
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ChampionSearchViewModel(
    private val championsRepository: ChampionsRepository
) : ViewModel(), LifecycleObserver {

    private val _list: MutableLiveData<List<ChampionShort>> = MutableLiveData()

    val championSearchEvents: SingleLiveEvent<ChampionSearchEvent> = SingleLiveEvent()

    internal val searchResource: CountingIdlingResource =
        CountingIdlingResource("champion-search", true)

    val list: LiveData<ChampionSearchResult>
        get() = _list.combineLatest(queryText) { list, query ->
            searchResource.perform {
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
        }

    val queryText: MutableLiveData<String> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onCreate() {
        viewModelScope.launch {
            _list.value = championsRepository.getChampions()
        }
    }

    fun onQueryChanged(query: String) {
        this.queryText.value = query
    }

    fun onChampionClicked(data: ChampionSearchViewData) {
        viewModelScope.launch {
            val champion: Champion = try {
                championsRepository.getChampion(data.champion.id)
            } catch (ex: Exception) {
                Log.e("ChampionSearch", "Couldn't load champion ${data.champion.name}", ex)
                null
            } ?: return@launch

            championSearchEvents.value = ChampionSearchEvent.OpenChampion(champion)
        }
    }

    fun onClearQueryClick() {
        this.queryText.value = ""
    }
}

sealed class ChampionSearchEvent {
    data class OpenChampion(val champion: Champion) : ChampionSearchEvent()
}