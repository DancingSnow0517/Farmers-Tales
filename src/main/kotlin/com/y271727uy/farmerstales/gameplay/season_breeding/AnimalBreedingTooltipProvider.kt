package com.y271727uy.farmerstales.gameplay.season_breeding

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.integration.IntegrationManager
import com.y271727uy.farmerstales.integration.sereneseasons.SeasonSupport
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.animal.Animal
import snownee.jade.api.EntityAccessor
import snownee.jade.api.IEntityComponentProvider
import snownee.jade.api.ITooltip
import snownee.jade.api.config.IPluginConfig

object AnimalBreedingTooltipProvider : IEntityComponentProvider {
    private val uid = ResourceLocation.fromNamespaceAndPath(FTMod.MODID, "animal_breeding_seasons")

    override fun getUid(): ResourceLocation = uid

    override fun appendTooltip(tooltip: ITooltip, accessor: EntityAccessor, config: IPluginConfig) {
        if (!IntegrationManager.isSereneSeasonsLoaded()) {
            return
        }
        val animal = accessor.entity as? Animal ?: return
        if (!BreedingSeasonRules.hasRule(animal.type)) {
            return
        }

        SeasonSupport.createSeasonInfoLines(
            "tooltip.${FTMod.MODID}.breeding_seasons",
            SeasonBreedingEvents.allowedSeasons(animal)
        ).forEach(tooltip::add)
    }
}
