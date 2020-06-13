package com.valhalla.lolchampviewer.ui.champion_history

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.net.models.Champion

class ChampionHistoryViewModel : ViewModel() {

    val splashImage: MutableLiveData<String> = MutableLiveData()

    val lore: MutableLiveData<String> = MutableLiveData()

    fun init(args: Bundle?) {
        val champion = args?.getSerializable("champion") as Champion
        splashImage.value = DataDragonApi.getSkinAddress(champion, champion.skins.first())
        lore.value = champion.lore
    }
}