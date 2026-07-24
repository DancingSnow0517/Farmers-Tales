package com.y271727uy.farmerstales.all

import com.tterrag.registrate.util.entry.ItemEntry
import com.y271727uy.farmerstales.all.items.BasicItems
import net.minecraft.world.item.Item

/** Aggregates item groups so the mod entry point does not know individual categories. */
object ModItems {
    @JvmField
    val EXAMPLE_ITEM: ItemEntry<Item> = BasicItems.EXAMPLE_ITEM

    fun init() {
        BasicItems.init()
    }
}
