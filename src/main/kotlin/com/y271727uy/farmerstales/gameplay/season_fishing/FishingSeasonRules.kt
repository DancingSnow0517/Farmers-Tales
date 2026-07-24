package com.y271727uy.farmerstales.gameplay.season_fishing

import com.y271727uy.farmerstales.integration.sereneseasons.SeasonSupport.SeasonWindow
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries
import java.util.EnumSet

/**
 * Describes seasonal fishing conditions for item tooltips. Catch replacement is
 * intentionally not handled here; the old global reroll behavior was incorrect.
 */
object FishingSeasonRules {
    private val rules = linkedMapOf<String, FishingRuleSnapshot>()

    init {
        fish("minecraft:cod")
            .catchSeasons(SeasonWindow.AUTUMN, SeasonWindow.WINTER)
            .register()
        fish("minecraft:salmon")
            .catchSeasons(SeasonWindow.SPRING, SeasonWindow.AUTUMN)
            .register()
        fish("minecraft:pufferfish")
            .catchSeasons(SeasonWindow.SUMMER, SeasonWindow.AUTUMN)
            .register()
        fish("minecraft:tropical_fish")
            .catchSeasons(SeasonWindow.SPRING, SeasonWindow.SUMMER)
            .register()
    }

    fun fish(itemId: String): RuleBuilder = RuleBuilder(itemId)

    fun describe(item: Item): FishingRuleSnapshot? = ForgeRegistries.ITEMS.getKey(item)?.toString()?.let(rules::get)

    class RuleBuilder internal constructor(private val itemId: String) {
        private var dimensions: List<ResourceLocation> = emptyList()
        private var dimensionsBlacklist: List<ResourceLocation> = emptyList()
        private var biomes: List<ResourceLocation> = emptyList()
        private var biomeTags: List<ResourceLocation> = emptyList()
        private var biomesBlacklist: List<ResourceLocation> = emptyList()
        private var biomeBlacklistTags: List<ResourceLocation> = emptyList()
        private var seasons: Set<SeasonWindow> = EnumSet.allOf(SeasonWindow::class.java)
        private var daytime = FishingDaytime.ALL
        private var weather = FishingWeather.ALL

        fun withDimensions(vararg values: ResourceLocation): RuleBuilder = apply { dimensions = values.toList() }

        fun withDimensionsBlacklist(vararg values: ResourceLocation): RuleBuilder = apply { dimensionsBlacklist = values.toList() }

        fun withBiomes(vararg values: ResourceLocation): RuleBuilder = apply { biomes = values.toList() }

        fun withBiomeTags(vararg values: ResourceLocation): RuleBuilder = apply { biomeTags = values.toList() }

        fun withBiomesBlacklist(vararg values: ResourceLocation): RuleBuilder = apply { biomesBlacklist = values.toList() }

        fun withBiomeBlacklistTags(vararg values: ResourceLocation): RuleBuilder = apply { biomeBlacklistTags = values.toList() }

        fun catchSeasons(vararg values: SeasonWindow): RuleBuilder = apply {
            seasons = if (values.isEmpty()) EnumSet.allOf(SeasonWindow::class.java) else EnumSet.copyOf(values.toList())
        }

        fun withDaytime(value: FishingDaytime): RuleBuilder = apply { daytime = value }

        fun withWeather(value: FishingWeather): RuleBuilder = apply { weather = value }

        fun register() {
            rules[itemId] = FishingRuleSnapshot(
                dimensions,
                dimensionsBlacklist,
                biomes,
                biomeTags,
                biomesBlacklist,
                biomeBlacklistTags,
                seasons,
                daytime,
                weather
            )
        }
    }
}

data class FishingRuleSnapshot(
    val dimensions: List<ResourceLocation>,
    val dimensionsBlacklist: List<ResourceLocation>,
    val biomes: List<ResourceLocation>,
    val biomeTags: List<ResourceLocation>,
    val biomesBlacklist: List<ResourceLocation>,
    val biomeBlacklistTags: List<ResourceLocation>,
    val seasons: Collection<SeasonWindow>,
    val daytime: FishingDaytime,
    val weather: FishingWeather
)

enum class FishingDaytime(val serializedName: String) {
    ALL("all"),
    DAY("day"),
    NOON("noon"),
    NIGHT("night"),
    MIDNIGHT("midnight")
}

enum class FishingWeather(val serializedName: String) {
    ALL("all"),
    CLEAR("clear"),
    RAIN("rain"),
    THUNDER("thunder")
}
