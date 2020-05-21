package com.valhalla.lolchampviewer.ui

import androidx.appcompat.app.AppCompatActivity
import com.valhalla.lolchampviewer.net.models.Champion

interface Wizard {

    companion object {
        const val ARG_CHAMPION = "champion"
    }

    fun attach(activity: AppCompatActivity)
    fun openChampionDetails(champion: Champion)
    fun openChampionHistory(champion: Champion)
    fun openChampionSkills(champion: Champion)
    fun detach()
}