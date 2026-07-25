package com.y271727uy.farmerstales.gameplay.season_fishing

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.integration.sereneseasons.SeasonSupport
import com.y271727uy.farmerstales.integration.sereneseasons.SeasonSupport.SeasonWindow
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.util.RandomSource
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.core.registries.Registries

object SeasonalFishingTags {
    private const val IN_SEASON_WEIGHT_MULTIPLIER = 1.5
    private const val OUT_OF_SEASON_WEIGHT_MULTIPLIER = 0.5

    private val seasonTags = linkedMapOf(
        SeasonWindow.SPRING to itemTag("spring_fish"),
        SeasonWindow.SUMMER to itemTag("summer_fish"),
        SeasonWindow.AUTUMN to itemTag("autumn_fish"),
        SeasonWindow.WINTER to itemTag("winter_fish")
    )

    private val vanillaFishWeights = mapOf(
        ResourceLocation.withDefaultNamespace("cod") to 60.0,
        ResourceLocation.withDefaultNamespace("salmon") to 25.0,
        ResourceLocation.withDefaultNamespace("pufferfish") to 13.0,
        ResourceLocation.withDefaultNamespace("tropical_fish") to 2.0
    )

    fun allowedSeasons(item: Item): List<SeasonWindow> = SpecialFishingRules
        .describe(BuiltInRegistries.ITEM.getKey(item))
        ?.seasons
        ?.toList()
        ?: taggedSeasons(item)

    fun isManagedFish(item: Item): Boolean = allowedSeasons(item).isNotEmpty()

    fun resolveCatch(level: Level, random: RandomSource): ResolvedFishingCatch {
        val currentSeason = SeasonSupport.currentSeasonWindow(level)
        val candidates = BuiltInRegistries.ITEM.asSequence()
            .mapNotNull(::candidateFor)
            .map { it.withWeight(currentSeason) }
            .toList()
        val baseWeight = candidates.sumOf(WeightedFish::baseWeight)
        val adjustedWeight = candidates.sumOf(WeightedFish::weight)
        val junkWeight = (baseWeight - adjustedWeight).coerceAtLeast(0.0)
        val totalWeight = adjustedWeight + junkWeight
        if (totalWeight <= 0.0) {
            return ResolvedFishingCatch.JUNK
        }

        var roll = random.nextDouble() * totalWeight
        candidates.forEach { candidate ->
            roll -= candidate.weight
            if (roll < 0.0) {
                return ResolvedFishingCatch.FISH(candidate.item)
            }
        }
        return ResolvedFishingCatch.JUNK
    }

    private fun itemTag(path: String): TagKey<Item> =
        TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FTMod.MODID, path))

    private fun baseWeight(item: Item): Double =
        vanillaFishWeights[BuiltInRegistries.ITEM.getKey(item)] ?: 1.0

    private fun taggedSeasons(item: Item): List<SeasonWindow> = seasonTags
        .filter { (_, tag) -> item.builtInRegistryHolder().`is`(tag) }
        .keys
        .toList()

    private fun candidateFor(item: Item): TaggedFish? {
        val specialRule = SpecialFishingRules.describe(BuiltInRegistries.ITEM.getKey(item))
        if (specialRule != null) {
            return TaggedFish(item, specialRule.seasons.toList(), specialRule.baseWeight ?: baseWeight(item))
        }

        val seasons = taggedSeasons(item)
        return seasons.takeIf(List<SeasonWindow>::isNotEmpty)?.let { TaggedFish(item, it, baseWeight(item)) }
    }

    private data class TaggedFish(val item: Item, val seasons: List<SeasonWindow>, val baseWeight: Double) {
        fun withWeight(currentSeason: SeasonWindow?): WeightedFish {
            val multiplier = if (currentSeason != null && currentSeason in seasons) {
                IN_SEASON_WEIGHT_MULTIPLIER
            } else {
                OUT_OF_SEASON_WEIGHT_MULTIPLIER
            }
            return WeightedFish(item, baseWeight, baseWeight * multiplier)
        }
    }

    private data class WeightedFish(val item: Item, val baseWeight: Double, val weight: Double)
}

sealed interface ResolvedFishingCatch {
    data class FISH(val item: Item) : ResolvedFishingCatch
    data object JUNK : ResolvedFishingCatch
}
