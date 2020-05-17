package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.Serializable

@Serializable
data class Champion(
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val image: Image,
    val skins: List<Skin>,
    val lore: String,
    val blurb: String,
    val allytips: List<String>,
    val enemytips: List<String>,
    val tags: List<String>,
    val partype: String,
    val info: Info,
    val stats: Stats
): java.io.Serializable