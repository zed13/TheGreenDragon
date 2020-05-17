package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.Serializable

@Serializable
data class Image (
    val full: String,
    val sprite: String,
    val group: String,
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int
): java.io.Serializable