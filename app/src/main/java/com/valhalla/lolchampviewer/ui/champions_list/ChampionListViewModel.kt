package com.valhalla.lolchampviewer.ui.champions_list

import android.util.Log
import androidx.lifecycle.*
import com.valhalla.lolchampviewer.core.SingleLiveEvent
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionShort
import com.valhalla.lolchampviewer.repository.ChampionsRepository
import kotlinx.coroutines.launch

class ChampionListViewModel(
    private val championsRepository: ChampionsRepository
) : ViewModel(), DefaultLifecycleObserver {

    val list: MutableLiveData<List<ChampionShort>> = MutableLiveData()
    val isItemLoading: MutableLiveData<Boolean> = MutableLiveData()
    val listState: MutableLiveData<ChampionListState> = MutableLiveData()
    val championListEvents: SingleLiveEvent<ChampionListEvent> = SingleLiveEvent()

    fun registerOn(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        load()
    }

    fun load() {
        viewModelScope.launch {
            listState.value = ChampionListState.Loading
            try {
                list.value = championsRepository.getChampions()
                if (list.value?.isNotEmpty() == true) {
                    listState.value = ChampionListState.Ready
                } else {
                    listState.value = ChampionListState.Empty
                }
            } catch (ex: Exception) {
                Log.i("ChampionsList", "Error is happened when load list", ex)
                listState.value = ChampionListState.Error(ex.message ?: "wtf")
            }
        }
    }

    fun onItemClick(champion: ChampionShort) {
        viewModelScope.launch {
            isItemLoading.value = true

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
                championListEvents.value = ChampionListEvent.OpenChampion(championFull)
            } else {
                championListEvents.value = ChampionListEvent.ChampionLoadingFailed(champion)
            }
            isItemLoading.value = false
        }
    }

    fun onSearchClick() {
        viewModelScope.launch {
            championListEvents.value = ChampionListEvent.OpenSearch
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

