package com.y271727uy.farmerstales.all.blocks

import com.tterrag.registrate.util.entry.BlockEntry
import com.y271727uy.farmerstales.registrate.ModRegistrate
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.MapColor

/** Small starter group; future crop, machine and decoration groups belong beside it. */
object BasicBlocks {
    @JvmField
    val EXAMPLE_BLOCK: BlockEntry<Block> = ModRegistrate.REGISTRATE
        .block("example_block") { properties ->
            Block(properties.mapColor(MapColor.STONE))
        }
        .simpleItem()
        // Keep the starter block usable before its own art assets are added.
        .blockstate { context, provider ->
            provider.simpleBlock(
                context.entry,
                provider.models().cubeAll(context.name, provider.mcLoc("block/stone"))
            )
        }
        .defaultLang()
        .register()

    fun init() = Unit
}
