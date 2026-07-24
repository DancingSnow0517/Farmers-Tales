package com.y271727uy.farmerstales.all

import com.tterrag.registrate.util.entry.BlockEntry
import com.y271727uy.farmerstales.all.blocks.BasicBlocks
import net.minecraft.world.level.block.Block

/** Aggregates block groups so the mod entry point does not know individual categories. */
object ModBlocks {
    @JvmField
    val EXAMPLE_BLOCK: BlockEntry<Block> = BasicBlocks.EXAMPLE_BLOCK

    fun init() {
        BasicBlocks.init()
    }
}
