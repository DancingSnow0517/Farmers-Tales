package com.y271727uy.farmerstales.integration.list

import com.y271727uy.farmerstales.FTMod
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent
import net.minecraftforge.eventbus.api.IEventBus

object ListIntegration {
    private val listTab = ResourceLocation("list", "list")

    fun init(modEventBus: IEventBus) {
        modEventBus.addListener(::addItemsToListTab)
        FTMod.LOGGER.debug("List item tab integration enabled")
    }

    private fun addItemsToListTab(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey.location() != listTab) {
            return
        }

        BuiltInRegistries.ITEM.forEach { item ->
            if (BuiltInRegistries.ITEM.getKey(item).namespace == FTMod.MODID) {
                event.accept(item)
            }
        }
    }
}
