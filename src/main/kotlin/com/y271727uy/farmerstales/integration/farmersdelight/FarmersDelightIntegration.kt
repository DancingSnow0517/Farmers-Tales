package com.y271727uy.farmerstales.integration.farmersdelight

import com.y271727uy.farmerstales.FTMod
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent
import net.minecraftforge.eventbus.api.IEventBus

object FarmersDelightIntegration {
    private val farmersDelightTab = ResourceLocation("farmersdelight", "farmersdelight")

    fun init(modEventBus: IEventBus) {
        modEventBus.addListener(::addItemsToFarmersDelightTab)
        FTMod.LOGGER.debug("Farmer's Delight item tab integration enabled")
    }

    private fun addItemsToFarmersDelightTab(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey.location() != farmersDelightTab) {
            return
        }

        BuiltInRegistries.ITEM.forEach { item ->
            if (BuiltInRegistries.ITEM.getKey(item).namespace == FTMod.MODID) {
                event.accept(item)
            }
        }
    }
}
