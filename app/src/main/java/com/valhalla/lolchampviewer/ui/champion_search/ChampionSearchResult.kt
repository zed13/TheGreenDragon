package com.valhalla.lolchampviewer.ui.champion_search

sealed class ChampionSearchResult {
    object NoQuery : ChampionSearchResult()
    data class NotFound(val query: String) : ChampionSearchResult()
    data class Filtered(val list: List<ChampionSearchViewData>) : ChampionSearchResult()
}