package com.valhalla.lolchampviewer.ui.champions_list

import android.util.Log
import androidx.lifecycle.*
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChampionListViewModel(
    private val championsRepository: ChampionsRepository
) : ViewModel(), LifecycleObserver {

    private val mainScope = MainScope()

    private val _list = MutableStateFlow<List<ChampionShort>>(emptyList())
    private val _isItemLoading = MutableStateFlow(false)
    private val _championListEvents: BroadcastChannel<ChampionListEvent> =
        BroadcastChannel(1)
    private val _listState = MutableStateFlow<ChampionListState>(ChampionListState.Loading)

    val list: Flow<List<ChampionShort>> = _list
    val isItemLoading: Flow<Boolean> = _isItemLoading
    val listState: Flow<ChampionListState> = _listState
    val championListEvents: Flow<ChampionListEvent>
        get() = _championListEvents.openSubscription().receiveAsFlow()

    private var job: Job? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        load()
    }

    fun load() {
        job?.cancel()
        job = viewModelScope.launch {
            _listState.value = ChampionListState.Loading
            try {
                _list.value = championsRepository.getChampions()
                if (_list.value.isNotEmpty()) {
                    _listState.value = ChampionListState.Ready
                } else {
                    _listState.value = ChampionListState.Empty
                }
            } catch (ex: Exception) {
                Log.i("ChampionsList", "Error is happened when load list", ex)
                _listState.value = ChampionListState.Error(ex.message ?: "wtf")
            }
        }
    }

    fun onItemClick(champion: ChampionShort) {
        viewModelScope.launch {
            _isItemLoading.value = true

            val championFull = try {
                championsRepository.getChampion(champion.id)
            } catch (ex: Exception) {
                Log.e(
                    "ChampionList",
                    "Error is happended when load champ info name=${champion.name}",
                    ex
                )
                null
            }
            if (championFull != null) {
                _championListEvents.send(ChampionListEvent.OpenChampion(championFull))
            } else {
                _championListEvents.send(ChampionListEvent.ChampionLoadingFailed(champion))
            }
            _isItemLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        mainScope.cancel()
    }

    fun onSearchClick() {
        viewModelScope.launch {
            _championListEvents.send(ChampionListEvent.OpenSearch)
        }
    }

}

sealed class ChampionListState {
    object Empty : ChampionListState()
    object Loading : ChampionListState()
    data class Error(val error: String) : ChampionListState()
    object Ready : ChampionListState()
}

sealed class ChampionListEvent {
    class ChampionLoadingFailed(val champion: ChampionShort) : ChampionListEvent()
    class OpenChampion(val champion: Champion) : ChampionListEvent()
    object OpenSearch : ChampionListEvent()
}

