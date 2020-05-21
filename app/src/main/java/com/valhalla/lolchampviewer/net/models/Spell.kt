package com.valhalla.lolchampviewer.net.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Spell(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("image") val image: Image
//    @SerialName("tooltip") val tooltip: String,
//    @SerialName("leveltip") val levelTip: LevelTip,
//    @SerialName("maxrank") val maxRank: Int,
//    @SerialName("cooldown") val cooldawn: List<Int>,
//    @SerialName("cooldownBurn") val cooldawnBurn: String,
//    @SerialName("cost") val cost: List<Int>,
//    @SerialName("costBurn") val costBurn: String,
//    @SerialName("datavalues") val dataValues: List<String>
) : java.io.Serializable