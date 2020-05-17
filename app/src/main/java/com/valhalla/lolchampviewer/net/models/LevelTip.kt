package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.Serializable

@Serializable
data class LevelTip(
    val label: List<String>,
    val effect: List<String>
): java.io.Serializable