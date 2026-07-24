package com.y271727uy.farmerstales

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.config.ModConfigEvent

@Mod.EventBusSubscriber(modid = FTMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object Config {
    private val builder = ForgeConfigSpec.Builder()

    private val logDirtBlockValue = builder
        .comment("Whether to log the dirt block on common setup")
        .define("logDirtBlock", true)

    private val magicNumberValue = builder
        .comment("A magic number")
        .defineInRange("magicNumber", 42, 0, Int.MAX_VALUE)

    private val magicNumberIntroductionValue = builder
        .comment("What you want the introduction message to be for the magic number")
        .define("magicNumberIntroduction", "The magic number is... ")

    private val itemStringsValue = builder
        .comment("A list of items to log on common setup")
        .defineListAllowEmpty("items", listOf("minecraft:iron_ingot")) { value ->
            value is String && BuiltInRegistries.ITEM.containsKey(ResourceLocation(value))
        }

    @JvmField
    val SPEC: ForgeConfigSpec = builder.build()

    var logDirtBlock: Boolean = true
        private set
    var magicNumber: Int = 42
        private set
    var magicNumberIntroduction: String = "The magic number is... "
        private set
    var items: Set<Item> = emptySet()
        private set

    @JvmStatic
    @SubscribeEvent
    fun onLoad(event: ModConfigEvent) {
        logDirtBlock = logDirtBlockValue.get()
        magicNumber = magicNumberValue.get()
        magicNumberIntroduction = magicNumberIntroductionValue.get()
        items = itemStringsValue.get()
            .map { BuiltInRegistries.ITEM.get(ResourceLocation(it)) }
            .toSet()
    }
}
