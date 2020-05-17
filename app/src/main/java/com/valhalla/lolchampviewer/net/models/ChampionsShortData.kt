package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.Serializable

@Serializable
data class ChampionsShortData(
    val type: String,
    val format: String,
    val version: String,
    val data: Map<String, ChampionShort>
): java.io.Serializable

