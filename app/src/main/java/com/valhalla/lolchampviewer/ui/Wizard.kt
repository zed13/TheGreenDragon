package com.valhalla.lolchampviewer.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.ui.champion_details_fragment.ChampionDetailsFragment

class Wizard {

    private var activity: AppCompatActivity? = null

    fun attach(activity: AppCompatActivity) {
        this.activity = activity
    }

    fun openChampionDetails(championFull: Champion) {
        activity?.supportFragmentManager
            ?.commit {
                add(
                    R.id.container,
                    ChampionDetailsFragment::class.java,
                    bundleOf("champion" to championFull),
                    "details"
                )
                addToBackStack(null)
            }
    }

    fun detach() {
        activity = null
    }
}