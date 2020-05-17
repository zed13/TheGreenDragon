package com.valhalla.lolchampviewer.ui.champion_details_fragment

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.Stats
import com.valhalla.lolchampviewer.ui.core.Data
import com.valhalla.lolchampviewer.ui.core.onlyPresent
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChampionDetailsViewModel : ViewModel() {

    private val _splashImage = MutableStateFlow<Data<String>>(Data.absent())
    private val _iconImage = MutableStateFlow<Data<String>>(Data.absent())
    private val _championData = MutableStateFlow<Data<ChampionViewData>>(Data.absent())
    private val _statsData = MutableStateFlow<List<StatViewData>>(emptyList())
    private val _skins = MutableStateFlow<List<String>>(emptyList())

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

    fun init(arguments: Bundle?) {
        val champion: Champion = arguments?.getSerializable("champion") as Champion

        viewModelScope.launch {
            _championData.value = Data.of(ChampionViewData(champion))
            _iconImage.value = Data.of(DataDragonApi.getIconAddress(champion.image.full))
            _splashImage.value =
                Data.of(DataDragonApi.getSkinAddress(champion, champion.skins.first()))
            _statsData.value = champion.stats.toList()
            _skins.value = champion.skins.map { DataDragonApi.getSkinAddress(champion, it) }
        }
    }
}

data class ChampionViewData(
    val name: String,
    val title: String,
    val blurb: String
) {
    constructor(champion: Champion) : this(
        name = champion.name,
        title = champion.title,
        blurb = champion.blurb
    )
}

data class StatViewData(
    val name: String,
    val initialValue: Double,
    val increasePerLevel: Double? = null
)

fun Stats.toList(): List<StatViewData> = listOf(
    StatViewData("Hit points", hp, hpperlevel),
    StatViewData("Mana points", mp, mpperlevel),
    StatViewData("Movement speed", movespeed),
    StatViewData("Armor", armor, armorperlevel),
    StatViewData("Spell block", spellblock, spellblockperlevel),
    StatViewData("Attack range", attackrange),
    StatViewData("HP regen", hpregen, hpregenperlevel),
    StatViewData("MP regen", mpregen, mpregenperlevel),
    StatViewData("Crit", crit, critperlevel),
    StatViewData("Attack damage", attackdamage, attackdamageperlevel),
    StatViewData("Attack speed", attackspeed)
)