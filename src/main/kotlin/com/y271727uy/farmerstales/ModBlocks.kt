package com.y271727uy.farmerstales

import com.tterrag.registrate.util.entry.BlockEntry
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor

object ModBlocks {
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
}
