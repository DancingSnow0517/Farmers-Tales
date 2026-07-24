package com.y271727uy.farmerstales.compat.jade

import com.y271727uy.farmerstales.FTMod
import snownee.jade.api.IWailaPlugin
import snownee.jade.api.WailaPlugin

object JadeCompat {
    fun init() {
        FTMod.LOGGER.debug("Jade compatibility enabled")
    }
}

@WailaPlugin
class FarmerTalesJadePlugin : IWailaPlugin
