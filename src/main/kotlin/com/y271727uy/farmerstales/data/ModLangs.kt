package com.y271727uy.farmerstales.data

import com.tterrag.registrate.providers.RegistrateLangProvider
import com.y271727uy.farmerstales.FTMod

object ModLangs {
    fun init(provider: RegistrateLangProvider) {
        provider.add("itemGroup.${FTMod.MODID}", "Farmer's Tales")
        provider.add("tooltip.${FTMod.MODID}.tree_seed.cultivable", "Can be cultivated on a tree compost!")
        provider.add("config.jade.plugin_${FTMod.MODID}.tree_stump_jade", "Farmer's Tales: Tree Stump")
        provider.add("tooltip.${FTMod.MODID}.jade.tree_stump.title", "Tree Stump Info")
        provider.add("tooltip.${FTMod.MODID}.jade.tree_stump.fertility", "Fertility: %s/25")
        provider.add("tooltip.${FTMod.MODID}.jade.tree_stump.water", "Water: %s/50")
        provider.add("tooltip.${FTMod.MODID}.jade.tree_stump.branches", "Branches: %s/25")
        provider.add("tooltip.${FTMod.MODID}.jade.tree_stump.maintenance_score", "Maintenance: %s/100")
        provider.add("tooltip.${FTMod.MODID}.jade.tree_stump.maintenance_active", "Tree maintenance is acceptable")
        provider.add("tooltip.${FTMod.MODID}.jade.tree_stump.maintenance_paused", "Tree maintenance is too low; growth is paused")
        provider.add("tooltip.${FTMod.MODID}.jade.tree_stump.maintenance_bonus", "Tree maintenance is excellent, growth speed +%s")
    }
}
