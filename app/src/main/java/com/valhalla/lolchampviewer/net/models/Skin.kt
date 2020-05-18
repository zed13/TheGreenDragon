package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Skin(
    @SerialName("id") val id: String,
    @SerialName("num") val num: Int,
    @SerialName("name") val name: String,
    @SerialName("chromas") val chromas: Boolean
): java.io.Serializable