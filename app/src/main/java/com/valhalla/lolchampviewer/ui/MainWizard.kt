package com.valhalla.lolchampviewer.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.ui.champion_details_fragment.ChampionDetailsFragment

class MainWizard : Wizard {

    private var activity: AppCompatActivity? = null

    override fun attach(activity: AppCompatActivity) {
        this.activity = activity
    }

    override fun openChampionDetails(championFull: Champion) {
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

    override fun openChampionHistory(champion: Champion) {

    }

    override fun openChampionSkills(champion: Champion) {

    }

    override fun detach() {
        activity = null
    }
}