package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val attack: Int,
    val defense: Int,
    val magic: Int,
    val difficulty: Int
): java.io.Serializable