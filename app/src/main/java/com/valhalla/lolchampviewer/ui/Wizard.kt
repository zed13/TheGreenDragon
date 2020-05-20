package com.valhalla.lolchampviewer.ui

import androidx.appcompat.app.AppCompatActivity
import com.valhalla.lolchampviewer.net.models.Champion

interface Wizard {
    fun attach(activity: AppCompatActivity)
    fun openChampionDetails(championFull: Champion)
    fun openChampionHistory(champion: Champion)
    fun openChampionSkills(champion: Champion)
    fun detach()
}