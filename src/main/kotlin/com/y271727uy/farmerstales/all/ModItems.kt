package com.y271727uy.farmerstales.all

import com.tterrag.registrate.util.entry.ItemEntry
import com.y271727uy.farmerstales.all.items.TreeItems
import net.minecraft.world.item.BlockItem

/** Aggregates item groups so the mod entry point does not know individual categories. */
object ModItems {
    @JvmField
    val TREE_COMPOST: ItemEntry<BlockItem> = TreeItems.TREE_COMPOST

    @JvmField
    val TREE_STUMP: ItemEntry<BlockItem> = TreeItems.TREE_STUMP

    fun init() {
        TreeItems.init()
    }
}
