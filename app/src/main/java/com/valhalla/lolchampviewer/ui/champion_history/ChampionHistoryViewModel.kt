package com.valhalla.lolchampviewer.ui.champion_history

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.ui.core.Data
import com.valhalla.lolchampviewer.ui.core.onlyPresent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ChampionHistoryViewModel : ViewModel() {

    private val _splashImage = MutableStateFlow<Data<String>>(Data.absent())
    private val _lore = MutableStateFlow<Data<String>>(Data.absent())

    val splashImage: Flow<String>
        get() = _splashImage.onlyPresent()

    val lore: Flow<String>
        get() = _lore.onlyPresent()


    fun init(args: Bundle?) {
        val champion = args?.getSerializable("champion") as Champion

        viewModelScope.launch {
            _splashImage.value =
                Data.of(DataDragonApi.getSkinAddress(champion, champion.skins.first()))
            _lore.value = Data.of(champion.lore)
        }
    }
}