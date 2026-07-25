package com.y271727uy.farmerstales.gameplay.season_fishing

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.integration.sereneseasons.SeasonSupport
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = FTMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
@Suppress("unused")
object FishingRuleTooltipEvents {
    @JvmStatic
    @SubscribeEvent
    fun onItemTooltip(event: ItemTooltipEvent) {
        val seasons = SeasonalFishingTags.allowedSeasons(event.itemStack.item)
        if (seasons.isNotEmpty()) {
            event.toolTip.addAll(SeasonSupport.createSeasonInfoLines("tooltip.${FTMod.MODID}.fishing_seasons", seasons))
        }
    }
}
