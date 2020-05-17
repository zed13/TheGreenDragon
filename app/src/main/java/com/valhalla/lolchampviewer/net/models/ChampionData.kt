package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.Serializable

@Serializable
data class ChampionData(
    val type: String,
    val format: String,
    val version: String,
    val data: Map<String, Champion>
): java.io.Serializable