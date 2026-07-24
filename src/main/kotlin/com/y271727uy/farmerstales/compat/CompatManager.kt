package com.y271727uy.farmerstales.compat

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.compat.jade.JadeCompat
import com.y271727uy.farmerstales.compat.jei.JeiCompat
import com.y271727uy.farmerstales.compat.sereneseasons.SereneSeasonsCompat
import net.minecraft.world.level.Level
import net.minecraftforge.fml.ModList

object CompatManager {
    const val JADE = "jade"
    const val JEI = "jei"
    const val SERENE_SEASONS = "sereneseasons"

    fun init() {
        if (isLoaded(JADE)) {
            JadeCompat.init()
        }
        if (isLoaded(JEI)) {
            JeiCompat.init()
        }
        if (isLoaded(SERENE_SEASONS)) {
            SereneSeasonsCompat.init()
        }
    }

    fun currentSeasonName(level: Level): String? {
        if (!isLoaded(SERENE_SEASONS)) {
            return null
        }
        return SereneSeasonsCompat.currentSeasonName(level)
    }

    private fun isLoaded(modId: String): Boolean {
        val loaded = ModList.get().isLoaded(modId)
        if (loaded) {
            FTMod.LOGGER.debug("Compatibility mod loaded: {}", modId)
        }
        return loaded
    }
}
