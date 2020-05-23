package com.valhalla.lolchampviewer.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.ui.Wizard.Companion.ARG_CHAMPION
import com.valhalla.lolchampviewer.ui.champion_details.ChampionDetailsFragment
import com.valhalla.lolchampviewer.ui.champion_history.ChampionHistoryFragment
import com.valhalla.lolchampviewer.ui.champion_skills.ChampionSkillsFragment

class MainWizard : Wizard {

    private var activity: AppCompatActivity? = null

    override fun attach(activity: AppCompatActivity) {
        this.activity = activity
    }

    override fun openChampionDetails(champion: Champion) {
        activity?.supportFragmentManager
            ?.commit {
                add(
                    R.id.container,
                    ChampionDetailsFragment::class.java,
                    bundleOf(ARG_CHAMPION to champion),
                    "champion_details"
                )
                addToBackStack(null)
            } ?: error("Activity not attached to wizard")
    }

    override fun openChampionHistory(champion: Champion) {
        activity?.supportFragmentManager
            ?.commit {
                add(
                    R.id.container,
                    ChampionHistoryFragment::class.java,
                    bundleOf(ARG_CHAMPION to champion),
                    "champion_history"
                )
                addToBackStack(null)
            } ?: error("Activity not attached to wizard")
    }

    override fun openChampionSkills(champion: Champion) {
        activity?.supportFragmentManager
            ?.commit {
                add(
                    R.id.container,
                    ChampionSkillsFragment::class.java,
                    bundleOf(ARG_CHAMPION to champion),
                    "champion_skills"
                )
                addToBackStack(null)
            } ?: error("Activity not attached to wizard")
    }

    override fun detach() {
        activity = null
    }
}