package com.y271727uy.farmerstales.gameplay.season_breeding

import com.y271727uy.farmerstales.integration.sereneseasons.SeasonSupport.SeasonWindow
import net.minecraft.world.entity.EntityType
import net.minecraftforge.registries.ForgeRegistries
import java.util.EnumSet

object BreedingSeasonRules {
    private val entityRules = linkedMapOf<String, Set<SeasonWindow>>()
    private val namespaceRules = linkedMapOf<String, Set<SeasonWindow>>()
    private var fallbackSeasons: Set<SeasonWindow> = EnumSet.allOf(SeasonWindow::class.java)

    init {
        animal("minecraft:cow").allYear().register()
        animal("minecraft:mooshroom").allYear().register()
        animal("minecraft:pig").allYear().register()
        animal("minecraft:sheep").seasons(SeasonWindow.AUTUMN, SeasonWindow.WINTER).register()
        animal("minecraft:goat").seasons(SeasonWindow.AUTUMN).register()
        animal("minecraft:chicken").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:rabbit").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:horse").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:donkey").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:llama").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:camel").seasons(SeasonWindow.WINTER, SeasonWindow.SPRING).register()
        animal("minecraft:wolf").seasons(SeasonWindow.WINTER, SeasonWindow.SPRING).register()
        animal("minecraft:fox").seasons(SeasonWindow.WINTER).register()
        animal("minecraft:cat").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER, SeasonWindow.AUTUMN).register()
        animal("minecraft:ocelot").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:panda").seasons(SeasonWindow.SPRING).register()
        animal("minecraft:bee").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:turtle").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:frog").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:axolotl").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:sniffer").seasons(SeasonWindow.SPRING, SeasonWindow.SUMMER).register()
        animal("minecraft:hoglin").allYear().register()
        animal("minecraft:strider").allYear().register()
    }

    fun animal(entityId: String): RuleBuilder = RuleBuilder(entityId, false)

    fun namespace(namespace: String): RuleBuilder = RuleBuilder(namespace, true)

    fun fallbackTo(vararg seasons: SeasonWindow) {
        fallbackSeasons = normalize(*seasons)
    }

    fun hasRule(entityType: EntityType<*>): Boolean {
        val id = entityId(entityType) ?: return false
        return id in entityRules || id.substringBefore(':') in namespaceRules
    }

    fun allowedSeasons(entityType: EntityType<*>): Collection<SeasonWindow> {
        val id = entityId(entityType) ?: return fallbackSeasons
        return entityRules[id] ?: namespaceRules[id.substringBefore(':')] ?: fallbackSeasons
    }

    private fun entityId(entityType: EntityType<*>): String? = ForgeRegistries.ENTITY_TYPES.getKey(entityType)?.toString()

    private fun normalize(vararg seasons: SeasonWindow): Set<SeasonWindow> =
        if (seasons.isEmpty()) EnumSet.allOf(SeasonWindow::class.java) else EnumSet.copyOf(seasons.toList())

    class RuleBuilder internal constructor(private val id: String, private val namespaceRule: Boolean) {
        private var seasons: Set<SeasonWindow> = EnumSet.allOf(SeasonWindow::class.java)

        fun seasons(vararg values: SeasonWindow): RuleBuilder = apply { seasons = normalize(*values) }

        fun allYear(): RuleBuilder = apply { seasons = EnumSet.allOf(SeasonWindow::class.java) }

        fun register() {
            if (namespaceRule) namespaceRules[id] = seasons else entityRules[id] = seasons
        }
    }
}
