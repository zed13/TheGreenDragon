package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.Serializable

@Serializable
data class Skin(
    val id: String,
    val num: Int,
    val name: String,
    val chromas: Boolean
): java.io.Serializable