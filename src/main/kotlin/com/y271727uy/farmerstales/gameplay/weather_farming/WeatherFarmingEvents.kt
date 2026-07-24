package com.y271727uy.farmerstales.gameplay.weather_farming

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.config.Config
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraftforge.event.level.BlockEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = FTMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@Suppress("unused")
object WeatherFarmingEvents {
    private val applyingRainBonus = ThreadLocal.withInitial { false }

    @JvmStatic
    @SubscribeEvent
    fun onCropGrowPre(event: BlockEvent.CropGrowEvent.Pre) {
        val level = event.level as? ServerLevel ?: return
        if (Config.weatherAffectsCropGrowth && isSnowFallingOn(level, event.pos)) {
            event.result = Event.Result.DENY
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onCropGrowPost(event: BlockEvent.CropGrowEvent.Post) {
        val level = event.level as? ServerLevel ?: return
        if (!Config.weatherAffectsCropGrowth || applyingRainBonus.get() || Config.rainGrowthBonusChance <= 0.0) {
            return
        }
        if (event.originalState == event.state || isSnowFallingOn(level, event.pos) || !isRainFallingOn(level, event.pos)) {
            return
        }
        if (level.random.nextDouble() >= Config.rainGrowthBonusChance) {
            return
        }

        val state = level.getBlockState(event.pos)
        if (!state.isRandomlyTicking) {
            return
        }

        applyingRainBonus.set(true)
        try {
            state.randomTick(level, event.pos, level.random)
        } finally {
            applyingRainBonus.set(false)
        }
    }

    private fun isRainFallingOn(level: ServerLevel, cropPos: BlockPos): Boolean = level.isRainingAt(cropPos.above())

    private fun isSnowFallingOn(level: ServerLevel, cropPos: BlockPos): Boolean {
        val precipitationPos = cropPos.above()
        return level.isRaining && level.canSeeSky(precipitationPos) && level.getBiome(precipitationPos).value().coldEnoughToSnow(precipitationPos)
    }
}
