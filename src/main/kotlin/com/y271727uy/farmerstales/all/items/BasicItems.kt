package com.y271727uy.farmerstales.all.items

import com.tterrag.registrate.util.entry.ItemEntry
import com.y271727uy.farmerstales.registrate.ModRegistrate
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item

/** Small starter group; future food, fish and ingredient groups belong beside it. */
object BasicItems {
    @JvmField
    val EXAMPLE_ITEM: ItemEntry<Item> = ModRegistrate.REGISTRATE
        .item("example_item") { properties ->
            Item(properties.food(FoodProperties.Builder().nutrition(1).saturationMod(2.0f).alwaysEat().build()))
        }
        // Keep the starter item usable before its own art assets are added.
        .model { context, provider ->
            provider.generated(context, provider.mcLoc("item/apple"))
        }
        .defaultLang()
        .tab(CreativeModeTabs.INGREDIENTS)
        .register()

    fun init() = Unit
}
