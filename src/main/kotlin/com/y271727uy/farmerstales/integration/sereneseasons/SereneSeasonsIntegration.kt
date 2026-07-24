package com.y271727uy.farmerstales.integration.sereneseasons

import com.y271727uy.farmerstales.FTMod
import net.minecraft.world.level.Level
import sereneseasons.api.season.SeasonHelper

object SereneSeasonsIntegration {
    fun init() {
        FTMod.LOGGER.debug("Serene Seasons compatibility enabled")
    }

    fun currentSeasonName(level: Level): String {
        return SeasonHelper.getSeasonState(level).season.name
    }
}
