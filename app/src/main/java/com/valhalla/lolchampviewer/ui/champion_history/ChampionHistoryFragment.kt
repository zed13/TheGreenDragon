package com.valhalla.lolchampviewer.ui.champion_history

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.ui.core.BaseFragment
import com.valhalla.lolchampviewer.ui.core.bindView
import org.koin.android.viewmodel.ext.android.viewModel

class ChampionHistoryFragment : BaseFragment(R.layout.fragment_champion_history) {

    private val splashView: ImageView? by bindView(R.id.splash)
    private val loreView: TextView? by bindView(R.id.lore)

    private val vm: ChampionHistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            vm.init(arguments)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.content)
            .setOnTouchListener { _, _ -> true }

        vm.splashImage bindTo ::setSplashImage
        vm.lore bindTo ::setLore
    }

    private fun setSplashImage(url: String) {
        Picasso.get()
            .load(url)
            .into(splashView)
    }

    private fun setLore(lore: String) {
        loreView?.text = lore
    }
}