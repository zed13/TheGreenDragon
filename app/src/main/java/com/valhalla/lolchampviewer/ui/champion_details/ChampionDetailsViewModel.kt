package com.valhalla.lolchampviewer.ui.champion_details

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.ui.core.Data
import com.valhalla.lolchampviewer.ui.core.onlyPresent
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChampionDetailsViewModel : ViewModel() {

    private lateinit var champion: Champion

    private val _splashImage = MutableStateFlow<Data<String>>(Data.absent())
    private val _iconImage = MutableStateFlow<Data<String>>(Data.absent())
    private val _championData = MutableStateFlow<Data<ChampionViewData>>(Data.absent())
    private val _statsData = MutableStateFlow<List<StatViewData>>(emptyList())
    private val _skins = MutableStateFlow<List<String>>(emptyList())

    private val _events = BroadcastChannel<ChampionDetailsEvent>(1)

    val splashImage: Flow<String>
        get() = _splashImage.onlyPresent()
            .onEach { Log.i("ChampionDetails", "splashImage=$it") }

    val iconImage: Flow<String>
        get() = _iconImage.onlyPresent()
            .onEach { Log.i("ChampionDetails", "iconImage=$it") }

    val championData: Flow<ChampionViewData>
        get() = _championData.onlyPresent()

    val statsData: Flow<List<StatViewData>>
        get() = _statsData

    val skins: Flow<List<String>>
        get() = _skins

    val events: Flow<ChampionDetailsEvent>
        get() = _events.openSubscription().receiveAsFlow()

    fun init(arguments: Bundle?) {
        champion = arguments?.getSerializable("champion") as Champion

        viewModelScope.launch {
            _championData.value = Data.of(ChampionViewData(champion))
            _iconImage.value = Data.of(DataDragonApi.getIconAddress(champion.image.full))
            _splashImage.value =
                Data.of(DataDragonApi.getSkinAddress(champion, champion.skins.first()))
            _statsData.value = champion.stats.toList()
            _skins.value = champion.skins.map { DataDragonApi.getSkinAddress(champion, it) }
        }
    }

    fun openLore() {
        viewModelScope.launch {
            _events.send(ChampionDetailsEvent.OpenHistory(champion))
        }
    }

    fun onSkillsClick() {
        viewModelScope.launch {
            _events.send(ChampionDetailsEvent.OpenSkills(champion))
        }
    }
}

sealed class ChampionDetailsEvent {
    data class OpenHistory(val champion: Champion) : ChampionDetailsEvent()
    data class OpenSkills(val champion: Champion) : ChampionDetailsEvent()
}