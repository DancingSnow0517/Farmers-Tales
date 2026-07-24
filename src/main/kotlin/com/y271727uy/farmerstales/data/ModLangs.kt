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
        provider.add("config.jade.plugin_${FTMod.MODID}.animal_breeding_seasons", "Animal breeding seasons")
        provider.add("config.jade.plugin_${FTMod.MODID}.animal_breeding_seasons.desc", "Show the breeding seasons for the targeted animal in Jade.")
        provider.add("message.${FTMod.MODID}.breeding_blocked", "This animal cannot breed in the current season. Breeding seasons: %s")
        provider.add("tooltip.${FTMod.MODID}.breeding_seasons", "Breeding seasons: %s")
        provider.add("season.${FTMod.MODID}.spring", "Spring")
        provider.add("season.${FTMod.MODID}.summer", "Summer")
        provider.add("season.${FTMod.MODID}.autumn", "Autumn")
        provider.add("season.${FTMod.MODID}.winter", "Winter")
        provider.add("season.${FTMod.MODID}.year_round", "Year-round")
        provider.add("season.${FTMod.MODID}.any", "Any")
        provider.add("tooltip.${FTMod.MODID}.fishing_seasons", "Catch seasons: %s")
        provider.add("tooltip.${FTMod.MODID}.fishing_dimensions", "Dimensions: %s")
        provider.add("tooltip.${FTMod.MODID}.fishing_dimensions_blacklist", "Blocked dimensions: %s")
        provider.add("tooltip.${FTMod.MODID}.fishing_biomes", "Biomes: %s")
        provider.add("tooltip.${FTMod.MODID}.fishing_biomes_blacklist", "Blocked biomes: %s")
        provider.add("tooltip.${FTMod.MODID}.fishing_time", "Time: %s")
        provider.add("tooltip.${FTMod.MODID}.fishing_weather", "Weather: %s")
        provider.add("season.${FTMod.MODID}.fishing_daytime.all", "Any time")
        provider.add("season.${FTMod.MODID}.fishing_daytime.day", "Day")
        provider.add("season.${FTMod.MODID}.fishing_daytime.noon", "Noon")
        provider.add("season.${FTMod.MODID}.fishing_daytime.night", "Night")
        provider.add("season.${FTMod.MODID}.fishing_daytime.midnight", "Midnight")
        provider.add("season.${FTMod.MODID}.fishing_weather.all", "Any weather")
        provider.add("season.${FTMod.MODID}.fishing_weather.clear", "Clear")
        provider.add("season.${FTMod.MODID}.fishing_weather.rain", "Rain")
        provider.add("season.${FTMod.MODID}.fishing_weather.thunder", "Thunder")
    }
}
