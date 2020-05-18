package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChampionShort(
    @SerialName("id") val id: String,
    @SerialName("key") val key: String,
    @SerialName("name") val name: String,
    @SerialName("title") val title: String,
    @SerialName("blurb") val blurb: String,
    @SerialName("info") val info: Info,
    @SerialName("image") val image: Image,
    @SerialName("tags") val tags: List<String>,
    @SerialName("partype") val partype: String,
    @SerialName("stats") val stats: Stats
) : java.io.Serializable {
    val intKey: Long by lazy { key.toLongOrNull() ?: 0 }
}