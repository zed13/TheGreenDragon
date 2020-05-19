package com.valhalla.lolchampviewer.net.models

import com.valhalla.lolchampviewer.net.CommonEnumSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = ChampionTypeSerializer::class)
enum class ChampionType {
    @SerialName("bruiser")
    BRUISER,
    @SerialName("gunslinger")
    GUNSLINGER
}

class ChampionTypeSerializer :
    CommonEnumSerializer<ChampionType>(ChampionType::class, ChampionType.BRUISER)
