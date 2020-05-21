package com.valhalla.lolchampviewer.ui.champion_details_fragment

import com.valhalla.lolchampviewer.net.models.Stats

data class StatViewData(
    val name: String,
    val initialValue: Double,
    val increasePerLevel: Double? = null
)

fun Stats.toList(): List<StatViewData> = listOf(
    StatViewData(
        "Hit points",
        hp,
        hpPerLevel
    ),
    StatViewData(
        "Mana points",
        mp,
        mpPerLevel
    ),
    StatViewData(
        "Movement speed",
        moveSpeed
    ),
    StatViewData(
        "Armor",
        armor,
        armorPerLevel
    ),
    StatViewData(
        "Spell block",
        spellBlock,
        spellBlockPerLevel
    ),
    StatViewData(
        "Attack range",
        attackRange
    ),
    StatViewData(
        "HP regen",
        hpRegen,
        hpRegenPerLevel
    ),
    StatViewData(
        "MP regen",
        mpRegen,
        mpRegenPerLevel
    ),
    StatViewData(
        "Crit",
        crit,
        critPerLevel
    ),
    StatViewData(
        "Attack damage",
        attackDamage,
        attackDamagePerLevel
    ),
    StatViewData(
        "Attack speed",
        attackSpeed
    )
)