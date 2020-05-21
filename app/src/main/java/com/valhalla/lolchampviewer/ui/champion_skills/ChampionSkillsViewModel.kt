package com.valhalla.lolchampviewer.ui.champion_skills

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.ui.Wizard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ChampionSkillsViewModel : ViewModel() {

    private val _list = MutableStateFlow<List<SpellData>>(emptyList())

    val list: Flow<List<SpellData>>
        get() = _list


    @ExperimentalStdlibApi
    fun init(args: Bundle?) {
        val champion = args?.getSerializable(Wizard.ARG_CHAMPION) as Champion
        _list.value = champion.getSpellData()
    }

}

