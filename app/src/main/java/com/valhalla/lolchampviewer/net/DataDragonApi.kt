package com.valhalla.lolchampviewer.net

import com.valhalla.lolchampviewer.BuildConfig
import com.valhalla.lolchampviewer.net.models.*
import retrofit2.http.GET
import retrofit2.http.Path

interface DataDragonApi {

    companion object {
        const val DEFAULT_VERSION = "10.9.1"
        const val DEFAULT_LOCALE = "en_US"

        const val API_ADDRESS = BuildConfig.SERVER_ADDRESS

        private val spellMapping = arrayOf("Q", "W", "E", "R")

        fun getIconAddress(iconId: String): String {
            return "$API_ADDRESS/img/champion/${iconId}"
        }

        fun getSkinAddress(champion: Champion, skin: Skin): String {
            return "$API_ADDRESS/img/champion/skin/${champion.id}_${skin.num}.jpg"
        }

        fun getPassiveIcon(passive: Passive): String {
            return "$API_ADDRESS/img/skill/passive/${passive.image.full}"
        }

        fun getSpellIcon(spell: Spell): String {
            return "$API_ADDRESS/img/skill/spell/${spell.image.full}"
        }
    }

    @GET("champions?take=100")
    suspend fun getChampions(
    ): ChampionsShortData

    @GET("champion/{championId}")
    suspend fun getChampion(
        @Path("championId") championId: String
    ): Champion

}