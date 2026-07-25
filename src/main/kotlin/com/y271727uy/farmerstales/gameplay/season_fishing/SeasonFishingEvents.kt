package com.y271727uy.farmerstales.gameplay.season_fishing

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.config.Config
import com.y271727uy.farmerstales.integration.IntegrationManager
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.LootParams
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import net.minecraftforge.common.ToolActions
import net.minecraftforge.event.entity.player.ItemFishedEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = FTMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@Suppress("unused")
object SeasonFishingEvents {
    @JvmStatic
    @SubscribeEvent
    fun onItemFished(event: ItemFishedEvent) {
        val player = event.entity
        if (player.level().isClientSide || !Config.restrictFishingLoot || !IntegrationManager.isSereneSeasonsLoaded()) {
            return
        }
        for (index in event.drops.lastIndex downTo 0) {
            val caught = event.drops[index]
            if (!SeasonalFishingTags.isManagedFish(caught.item)) {
                continue
            }

            when (val resolved = SeasonalFishingTags.resolveCatch(player.level(), player.random)) {
                is ResolvedFishingCatch.FISH -> event.drops[index] = ItemStack(resolved.item, caught.count)
                ResolvedFishingCatch.JUNK -> {
                    val junk = rollVanillaJunk(event, player)
                    if (junk.isNotEmpty()) {
                        event.drops.removeAt(index)
                        event.drops.addAll(index, junk)
                    }
                }
            }
        }

    }

    private fun rollVanillaJunk(event: ItemFishedEvent, player: net.minecraft.world.entity.player.Player): List<ItemStack> {
        val level = player.level() as ServerLevel
        val hook = event.hookEntity
        val rod = sequenceOf(player.mainHandItem, player.offhandItem)
            .firstOrNull { it.canPerformAction(ToolActions.FISHING_ROD_CAST) }
            ?: player.mainHandItem
        val params = LootParams.Builder(level)
            .withParameter(LootContextParams.ORIGIN, hook.position())
            .withParameter(LootContextParams.TOOL, rod)
            .withParameter(LootContextParams.THIS_ENTITY, hook)
            .withOptionalParameter(LootContextParams.KILLER_ENTITY, hook.owner)
            .withLuck(player.luck)
            .create(LootContextParamSets.FISHING)
        return level.server.lootData.getLootTable(BuiltInLootTables.FISHING_JUNK).getRandomItems(params)
    }
}
