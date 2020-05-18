package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LevelTip(
    @SerialName("label") val label: List<String>,
    @SerialName("effect") val effect: List<String>
): java.io.Serializable