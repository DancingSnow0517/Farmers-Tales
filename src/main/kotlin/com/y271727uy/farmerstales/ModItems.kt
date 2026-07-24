package com.y271727uy.farmerstales

import com.tterrag.registrate.util.entry.ItemEntry
import net.minecraft.world.item.Item
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.food.FoodProperties

object ModItems {
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
}
