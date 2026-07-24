package com.y271727uy.farmerstales.all.items

import com.tterrag.registrate.util.entry.ItemEntry
import com.y271727uy.farmerstales.all.ModBlocks
import com.y271727uy.farmerstales.registrate.ModRegistrate
import net.minecraft.world.item.BlockItem

object TreeItems {
    @JvmField
    val TREE_COMPOST: ItemEntry<BlockItem> = ModRegistrate.REGISTRATE
        .item("tree_compost") { properties -> BlockItem(ModBlocks.TREE_COMPOST.get(), properties) }
        .model { _, provider -> provider.blockItem { ModBlocks.TREE_COMPOST.get() } }
        .register()

    @JvmField
    val TREE_STUMP: ItemEntry<BlockItem> = ModRegistrate.REGISTRATE
        .item("tree_stump") { properties -> BlockItem(ModBlocks.TREE_STUMP.get(), properties) }
        .model { _, provider -> provider.blockItem { ModBlocks.TREE_STUMP.get() } }
        .register()

    fun init() = Unit
}
