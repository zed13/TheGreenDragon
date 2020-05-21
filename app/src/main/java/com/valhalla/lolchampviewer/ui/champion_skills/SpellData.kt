package com.valhalla.lolchampviewer.ui.champion_skills

import com.valhalla.lolchampviewer.net.DataDragonApi
import com.valhalla.lolchampviewer.net.models.Champion

data class SpellData(
    val title: String,
    val description: String,
    val imageUrl: String
)

@ExperimentalStdlibApi
fun Champion.getSpellData(): List<SpellData> {
    return buildList {
        add(
            SpellData(
                title = passive.name,
                description = passive.description,
                imageUrl = DataDragonApi.getPassiveIcon(passive)
            )
        )

        spells.forEach {
            add(
                SpellData(
                    title = it.name,
                    description = it.description,
                    imageUrl = DataDragonApi.getSpellIcon(it)
                )
            )
        }
    }
}