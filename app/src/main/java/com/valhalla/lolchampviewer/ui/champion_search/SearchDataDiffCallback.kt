package com.valhalla.lolchampviewer.ui.champion_search

import androidx.recyclerview.widget.DiffUtil

class SearchDataDiffCallback(
    val oldItems: List<ChampionSearchViewData>,
    val newItems: List<ChampionSearchViewData>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].champion.id == newItems[newItemPosition].champion.id
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}