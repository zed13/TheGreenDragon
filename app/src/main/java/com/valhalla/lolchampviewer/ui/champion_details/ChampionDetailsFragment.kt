package com.valhalla.lolchampviewer.ui.champion_details

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.ui.core.BaseFragment
import com.valhalla.lolchampviewer.ui.core.bindView
import com.valhalla.lolchampviewer.ui.core.onClick
import org.koin.android.viewmodel.ext.android.viewModel

class ChampionDetailsFragment : BaseFragment(R.layout.fragment_champion_details) {

    private val vm: ChampionDetailsViewModel by viewModel()

    private val splashView: ImageView? by bindView(R.id.splash)
    private val logoView: ImageView? by bindView(R.id.logo)
    private val nameView: TextView? by bindView(R.id.name)
    private val titleView: TextView? by bindView(R.id.title)
    private val blurbView: TextView? by bindView(R.id.blurb)
    private val statsContainerView: LinearLayout? by bindView(R.id.stats_container)
    private val skinsCarouselView: RecyclerView? by bindView(R.id.skins_carousel)

    private val skinsAdapter = SkinsCarouselAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            vm.init(arguments)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.content).setOnTouchListener { _, _ -> true }

        skinsCarouselView?.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        skinsCarouselView?.adapter = skinsAdapter

        onClick(R.id.skills_button) { vm.onSkillsClick() }

        onClick(R.id.read_lore) { vm.openLore() }

        vm.splashImage bindTo ::setSplash
        vm.iconImage bindTo ::setIcon
        vm.championData bindTo ::setChampionData
        vm.statsData bindTo ::setStats
        vm.skins bindTo ::setSkins
        vm.championDetailsEvents bindTo ::handleEvent
    }

    private fun setSplash(splashUrl: String) {
        Picasso.get()
            .load(splashUrl)
            .into(splashView)
    }

    private fun setIcon(iconUrl: String) {
        Picasso.get()
            .load(iconUrl)
            .into(logoView)
    }

    private fun setChampionData(data: ChampionViewData) {
        nameView?.text = data.name
        titleView?.text = data.title
        blurbView?.text = data.blurb
    }

    private fun setStats(stats: List<StatViewData>) {
        statsContainerView?.removeAllViews()

        val view = view ?: return

        for (stat in stats) {
            val textView = TextView(view.context)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            textView.setTextColor(Color.BLACK)
            textView.text = stat.format()
            statsContainerView?.addView(
                textView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun setSkins(skins: List<String>) {
        skinsAdapter.items = skins
    }

    private fun handleEvent(event: ChampionDetailsEvent) {
        when (event) {
            is ChampionDetailsEvent.OpenHistory -> {
                findNavController().navigate(
                    R.id.action_championDetails_to_championHistory,
                    bundleOf("champion" to event.champion)
                )
            }
            is ChampionDetailsEvent.OpenSkills -> {
                findNavController().navigate(
                    R.id.action_championDetails_to_championSkills,
                    bundleOf("champion" to event.champion)
                )
            }
        }
    }
}