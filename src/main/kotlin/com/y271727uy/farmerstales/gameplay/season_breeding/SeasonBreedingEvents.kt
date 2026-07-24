package com.y271727uy.farmerstales.gameplay.season_breeding

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.config.Config
import com.y271727uy.farmerstales.integration.IntegrationManager
import com.y271727uy.farmerstales.integration.sereneseasons.SeasonSupport
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = FTMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@Suppress("unused")
object SeasonBreedingEvents {
    @JvmStatic
    @SubscribeEvent
    fun onAnimalInteract(event: PlayerInteractEvent.EntityInteract) {
        val animal = event.target as? Animal ?: return
        if (!shouldRestrict(animal) || !isBreedingAttempt(animal, event.itemStack)) {
            return
        }

        event.setCancellationResult(InteractionResult.FAIL)
        event.isCanceled = true
        SeasonSupport.sendPlayerFeedback(event.entity, blockedMessage(animal))
    }

    @JvmStatic
    @SubscribeEvent
    fun onBabyEntitySpawn(event: BabyEntitySpawnEvent) {
        val animal = event.parentA as? Animal ?: return
        if (!shouldRestrict(animal)) {
            return
        }

        event.isCanceled = true
        event.causedByPlayer?.let { SeasonSupport.sendPlayerFeedback(it, blockedMessage(animal)) }
    }

    fun allowedSeasons(animal: Animal) = BreedingSeasonRules.allowedSeasons(animal.type)

    private fun shouldRestrict(animal: Animal): Boolean {
        if (!Config.restrictAnimalBreeding || !IntegrationManager.isSereneSeasonsLoaded()) {
            return false
        }
        if (!BreedingSeasonRules.hasRule(animal.type)) {
            return false
        }

        val currentSeason = SeasonSupport.currentSeasonWindow(animal.level())
        return currentSeason != null && currentSeason !in allowedSeasons(animal)
    }

    private fun isBreedingAttempt(animal: Animal, heldItem: ItemStack): Boolean =
        animal.isFood(heldItem) && animal.canFallInLove()

    private fun blockedMessage(animal: Animal): Component = Component.translatable(
        "message.${FTMod.MODID}.breeding_blocked",
        SeasonSupport.formatSeasonsInlineOrYearRound(allowedSeasons(animal))
    )
}
