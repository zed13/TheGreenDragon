package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChampionData(
    @SerialName("type") val type: String,
    @SerialName("format") val format: String,
    @SerialName("version") val version: String,
    @SerialName("data") val data: Map<String, Champion>
): java.io.Serializable