package com.valhalla.lolchampviewer.net

import com.valhalla.lolchampviewer.net.models.Champion
import com.valhalla.lolchampviewer.net.models.ChampionData
import com.valhalla.lolchampviewer.net.models.ChampionsShortData
import com.valhalla.lolchampviewer.net.models.Skin
import retrofit2.http.GET
import retrofit2.http.Path

interface DataDragonApi {

    companion object {
        const val DEFAULT_VERSION = "10.9.1"
        const val DEFAULT_LOCALE = "en_US"

        const val API_ADDRESS = "http://192.168.0.51:8080"

        fun getIconAddress(iconId: String): String {
            return "$API_ADDRESS/$DEFAULT_VERSION/img/champion/${iconId}"
        }

        fun getSkinAddress(champion: Champion, skin: Skin): String {
            return "$API_ADDRESS/img/champion/splash/${champion.id}_${skin.num}.jpg"
        }
    }

    @GET("{version}/data/{locale}/champion.json")
    suspend fun getChampions(
        @Path("version") version: String = DEFAULT_VERSION,
        @Path("locale") locale: String = DEFAULT_LOCALE
    ): ChampionsShortData

    @GET("{version}/data/{locale}/champion/{championId}.json")
    suspend fun getChampion(
        @Path("version") version: String = DEFAULT_VERSION,
        @Path("locale") locale: String = DEFAULT_LOCALE,
        @Path("championId") championId: String
    ): ChampionData
}