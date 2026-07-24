package com.y271727uy.farmerstales.integration.jei

import com.y271727uy.farmerstales.FTMod
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import net.minecraft.resources.ResourceLocation

object JeiIntegration {
    fun init() {
        FTMod.LOGGER.debug("JEI compatibility enabled")
    }
}

@JeiPlugin
class FarmerTalesJeiPlugin : IModPlugin {
    override fun getPluginUid(): ResourceLocation {
        return ResourceLocation(FTMod.MODID, "jei_plugin")
    }
}
