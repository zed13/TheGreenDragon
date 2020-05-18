package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image (
    @SerialName("full") val full: String,
    @SerialName("sprite") val sprite: String,
    @SerialName("group") val group: String,
    @SerialName("x") val x: Int,
    @SerialName("y") val y: Int,
    @SerialName("w") val w: Int,
    @SerialName("h") val h: Int
): java.io.Serializable