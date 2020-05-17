package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.Serializable

@Serializable
data class ChampionShort(
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val blurb: String,
    val info: Info,
    val image: Image,
    val tags: List<String>,
    val partype: String,
    val stats: Stats
) : java.io.Serializable {
    val intKey: Long by lazy { key.toLongOrNull() ?: 0 }
}