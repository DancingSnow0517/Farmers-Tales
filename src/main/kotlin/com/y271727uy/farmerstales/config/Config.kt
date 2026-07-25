package com.y271727uy.farmerstales.config

import com.y271727uy.farmerstales.FTMod
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.config.ModConfigEvent

@Mod.EventBusSubscriber(modid = FTMod.Companion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object Config {
    private val builder = ForgeConfigSpec.Builder()

    private val restrictAnimalBreedingValue = builder
        .comment("Whether animal breeding is blocked outside each animal's breeding season")
        .define("restrictAnimalBreeding", true)

    private val restrictFishingLootValue = builder
        .comment("Whether seasonal fishing weights apply to fishing catches")
        .define("restrictFishingLoot", true)

    private val sendActionBarFeedbackValue = builder
        .comment("Whether players receive an action bar hint when seasonal breeding is blocked")
        .define("sendActionBarFeedback", true)

    private val weatherAffectsCropGrowthValue = builder
        .comment("Whether rain and snow affect crop growth")
        .define("weatherAffectsCropGrowth", true)

    private val rainGrowthBonusChanceValue = builder
        .comment("Chance for rain-exposed crops to receive one extra random growth tick")
        .defineInRange("rainGrowthBonusChance", 0.35, 0.0, 1.0)

    @JvmField
    val SPEC: ForgeConfigSpec = builder.build()

    var restrictAnimalBreeding: Boolean = true
        private set
    var restrictFishingLoot: Boolean = true
        private set
    var sendActionBarFeedback: Boolean = true
        private set
    var weatherAffectsCropGrowth: Boolean = true
        private set
    var rainGrowthBonusChance: Double = 0.35
        private set

    @JvmStatic
    @SubscribeEvent
    fun onLoad(event: ModConfigEvent) {
        if (event.config.getSpec() !== SPEC) {
            return
        }

        restrictAnimalBreeding = restrictAnimalBreedingValue.get()
        restrictFishingLoot = restrictFishingLootValue.get()
        sendActionBarFeedback = sendActionBarFeedbackValue.get()
        weatherAffectsCropGrowth = weatherAffectsCropGrowthValue.get()
        rainGrowthBonusChance = rainGrowthBonusChanceValue.get()
    }
}
