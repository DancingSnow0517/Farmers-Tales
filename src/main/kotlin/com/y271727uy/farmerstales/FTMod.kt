package com.y271727uy.farmerstales

import com.mojang.logging.LogUtils
import com.y271727uy.farmerstales.all.ModBlocks
import com.y271727uy.farmerstales.all.ModItems
import com.y271727uy.farmerstales.config.Config
import com.y271727uy.farmerstales.data.ModDataGen
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.slf4j.Logger

@Mod(FTMod.MODID)
class FTMod {
    init {
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus

        ModItems.init()
        ModBlocks.init()
        ModDataGen.init()
        modEventBus.addListener(::commonSetup)
        MinecraftForge.EVENT_BUS.register(this)
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC)
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        LOGGER.info("Farmer's Tales common setup complete")
    }

    companion object {
        const val MODID = "farmerstales"
        val LOGGER: Logger = LogUtils.getLogger()
    }
}
