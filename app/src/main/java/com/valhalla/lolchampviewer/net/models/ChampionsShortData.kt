package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChampionsShortData(
    @SerialName("total") val total: Int,
    @SerialName("items") val items: List<ChampionShort>
): java.io.Serializable

