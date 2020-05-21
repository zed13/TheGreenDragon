package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Passive(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("image") val image: Image
) : java.io.Serializable