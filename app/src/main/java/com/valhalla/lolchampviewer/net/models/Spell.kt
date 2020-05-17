package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.Serializable

@Serializable
data class Spell(
    val id: String,
    val name: String,
    val description: String,
    val tooltip: LevelTip
): java.io.Serializable