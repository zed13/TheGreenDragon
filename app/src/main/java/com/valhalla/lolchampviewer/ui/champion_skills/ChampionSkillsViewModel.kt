package com.valhalla.lolchampviewer.ui.champion_skills

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.valhalla.lolchampviewer.net.models.Champion

class ChampionSkillsViewModel : ViewModel() {

    val list: MutableLiveData<List<SpellData>> = MutableLiveData()

    @ExperimentalStdlibApi
    fun init(args: Bundle?) {
        val champion = args?.getSerializable("champion") as Champion
        list.value = champion.getSpellData()
    }

}

