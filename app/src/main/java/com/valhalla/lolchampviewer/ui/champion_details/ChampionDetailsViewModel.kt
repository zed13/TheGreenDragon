package com.valhalla.lolchampviewer.ui.champion_details

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valhalla.lolchampviewer.core.SingleLiveEvent
import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.net.models.Champion
import kotlinx.coroutines.launch

class ChampionDetailsViewModel : ViewModel() {

    private lateinit var champion: Champion

    val splashImage: MutableLiveData<String> = MutableLiveData()

    val iconImage: MutableLiveData<String> = MutableLiveData()

    val championData: MutableLiveData<ChampionViewData> = MutableLiveData()

    val statsData: MutableLiveData<List<StatViewData>> = MutableLiveData()

    val skins: MutableLiveData<List<String>> = MutableLiveData()

    val championDetailsEvents: SingleLiveEvent<ChampionDetailsEvent> = SingleLiveEvent()

    fun init(arguments: Bundle?) {
        champion = arguments?.getSerializable("champion") as Champion

        viewModelScope.launch {
            championData.value = ChampionViewData(champion)
            iconImage.value = DataDragonApi.getIconAddress(champion.image.full)
            splashImage.value = DataDragonApi.getSkinAddress(champion, champion.skins.first())
            statsData.value = champion.stats.toList()
            skins.value = champion.skins.map { DataDragonApi.getSkinAddress(champion, it) }
        }
    }

    fun openLore() {
        viewModelScope.launch {
            championDetailsEvents.value = ChampionDetailsEvent.OpenHistory(champion)
        }
    }

    fun onSkillsClick() {
        viewModelScope.launch {
            championDetailsEvents.value = ChampionDetailsEvent.OpenSkills(champion)
        }
    }
}

sealed class ChampionDetailsEvent {
    data class OpenHistory(val champion: Champion) : ChampionDetailsEvent()
    data class OpenSkills(val champion: Champion) : ChampionDetailsEvent()
}