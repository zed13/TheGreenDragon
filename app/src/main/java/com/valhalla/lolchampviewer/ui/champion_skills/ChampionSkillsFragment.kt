package com.valhalla.lolchampviewer.ui.champion_skills

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valhalla.lolchampviewer.R
import com.valhalla.lolchampviewer.ui.core.BaseFragment
import com.valhalla.lolchampviewer.ui.core.bindView
import org.koin.android.viewmodel.ext.android.viewModel

class ChampionSkillsFragment : BaseFragment(R.layout.fragment_chamion_skills) {

    private val listView: RecyclerView? by bindView(R.id.list)

    private val vm: ChampionSkillsViewModel by viewModel()

    private val spellAdapter = SpellAdapter()

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            vm.init(arguments)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setOnTouchListener { _, _ -> true }

        listView?.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = spellAdapter
        }

        vm.list bindTo { spellAdapter.items = it }
    }
}