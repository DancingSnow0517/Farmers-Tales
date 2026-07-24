package com.y271727uy.farmerstales.data

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.registrate.ModRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = FTMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object ModDataGen {
    @JvmStatic
    @SubscribeEvent
    @Suppress("UNUSED_PARAMETER")
    fun gatherData(event: GatherDataEvent) {
        // Custom server/client providers can be added here as the content grows.
    }

    fun init() {
        ModRegistrate.REGISTRATE.addDataGenerator(ProviderType.LANG) { provider ->
            ModLangs.init(provider)
        }
        ModRegistrate.REGISTRATE.addDataGenerator(ProviderType.ITEM_MODEL) { provider ->
            ModModels.initItem(provider)
        }
    }
}
