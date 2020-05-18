package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Champion(
    @SerialName("id") val id: String,
    @SerialName("key") val key: String,
    @SerialName("name") val name: String,
    @SerialName("title") val title: String,
    @SerialName("image") val image: Image,
    @SerialName("skins") val skins: List<Skin>,
    @SerialName("lore") val lore: String,
    @SerialName("blurb") val blurb: String,
    @SerialName("allytips") val allTips: List<String>,
    @SerialName("enemytips") val enemyTips: List<String>,
    @SerialName("tags") val tags: List<String>,
    @SerialName("partype") val partype: String,
    @SerialName("info") val info: Info,
    @SerialName("stats") val stats: Stats
): java.io.Serializable