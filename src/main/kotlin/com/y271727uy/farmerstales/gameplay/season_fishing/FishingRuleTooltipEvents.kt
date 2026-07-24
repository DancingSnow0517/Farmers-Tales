package com.y271727uy.farmerstales.gameplay.season_fishing

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.integration.sereneseasons.SeasonSupport
import com.y271727uy.farmerstales.integration.sereneseasons.SeasonSupport.SeasonWindow
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
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
        val rule = FishingSeasonRules.describe(event.itemStack.item) ?: return

        rule.dimensions.takeIf(List<ResourceLocation>::isNotEmpty)?.let {
            event.toolTip.add(Component.translatable("tooltip.${FTMod.MODID}.fishing_dimensions", SeasonSupport.formatResourceLocationsInlineOrAny(it)))
        }
        rule.dimensionsBlacklist.takeIf(List<ResourceLocation>::isNotEmpty)?.let {
            event.toolTip.add(Component.translatable("tooltip.${FTMod.MODID}.fishing_dimensions_blacklist", SeasonSupport.formatResourceLocationsInlineOrAny(it)))
        }
        if (rule.biomes.isNotEmpty() || rule.biomeTags.isNotEmpty()) {
            event.toolTip.add(Component.translatable("tooltip.${FTMod.MODID}.fishing_biomes", combine(rule.biomes, rule.biomeTags)))
        }
        if (rule.biomesBlacklist.isNotEmpty() || rule.biomeBlacklistTags.isNotEmpty()) {
            event.toolTip.add(Component.translatable("tooltip.${FTMod.MODID}.fishing_biomes_blacklist", combine(rule.biomesBlacklist, rule.biomeBlacklistTags)))
        }
        if (rule.daytime != FishingDaytime.ALL) {
            event.toolTip.add(Component.translatable("tooltip.${FTMod.MODID}.fishing_time", Component.translatable("season.${FTMod.MODID}.fishing_daytime.${rule.daytime.serializedName}")))
        }
        if (rule.weather != FishingWeather.ALL) {
            event.toolTip.add(Component.translatable("tooltip.${FTMod.MODID}.fishing_weather", Component.translatable("season.${FTMod.MODID}.fishing_weather.${rule.weather.serializedName}")))
        }
        if (rule.seasons.size != SeasonWindow.entries.size) {
            event.toolTip.addAll(SeasonSupport.createSeasonInfoLines("tooltip.${FTMod.MODID}.fishing_seasons", rule.seasons))
        }
    }

    private fun combine(exact: List<ResourceLocation>, tags: List<ResourceLocation>): Component {
        if (exact.isEmpty()) {
            return SeasonSupport.formatResourceLocationsInlineOrAny(tags, "#")
        }
        if (tags.isEmpty()) {
            return SeasonSupport.formatResourceLocationsInlineOrAny(exact)
        }

        val result: MutableComponent = Component.empty()
        result.append(SeasonSupport.formatResourceLocationsInlineOrAny(exact))
        result.append(Component.literal(", ").withStyle(ChatFormatting.GRAY))
        result.append(SeasonSupport.formatResourceLocationsInlineOrAny(tags, "#"))
        return result
    }
}
