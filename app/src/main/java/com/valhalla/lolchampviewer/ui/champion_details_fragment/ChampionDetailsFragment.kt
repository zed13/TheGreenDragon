package com.valhalla.lolchampviewer.ui.champion_details_fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.ui.core.BaseFragment
import com.valhalla.lolchampviewer.ui.picasso.intoImageView
import com.valhalla.lolchampviewer.ui.core.bindView
import com.valhalla.lolchampviewer.ui.core.onClick
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    private val mainScope = MainScope()
    private val skinsAdapter = SkinsCarouselAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val champion: Champion = arguments?.getSerializable("champion") as Champion
        Log.i("ChampionDetails", "champion received name=${champion.name}")
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

        onClick(R.id.skills_button) {
            Toast.makeText(view.context, "Skills button is clicked", Toast.LENGTH_SHORT).show()
        }

        vm.splashImage bindTo ::setSplash
        vm.iconImage bindTo ::setIcon
        vm.championData bindTo ::setChampionData
        vm.statsData bindTo ::setStats
        vm.skins bindTo ::setSkins
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
            textView.setStatViewData(stat)
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

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}

internal fun TextView.setStatViewData(data: StatViewData) {
    if (data.increasePerLevel != null) {
        text = String.format(
            "%s: %.1f (+ %.1f per level)",
            data.name,
            data.initialValue,
            data.increasePerLevel
        )
    } else {
        text = String.format("%s: %.1f", data.name, data.initialValue)
    }
}