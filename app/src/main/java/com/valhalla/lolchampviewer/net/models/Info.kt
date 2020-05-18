package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Info(
    @SerialName("attack") val attack: Int,
    @SerialName("defense") val defense: Int,
    @SerialName("magic") val magic: Int,
    @SerialName("difficulty") val difficulty: Int
): java.io.Serializable