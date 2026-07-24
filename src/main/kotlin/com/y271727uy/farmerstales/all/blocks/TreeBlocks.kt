package com.y271727uy.farmerstales.all.blocks

import com.tterrag.registrate.util.entry.BlockEntry
import com.y271727uy.farmerstales.gameplay.tree.block.TreeCompostBlock
import com.y271727uy.farmerstales.gameplay.tree.block.TreeStumpBlock
import com.y271727uy.farmerstales.registrate.ModRegistrate
import com.tterrag.registrate.providers.ProviderType
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour

object TreeBlocks {
    @JvmField
    val TREE_COMPOST: BlockEntry<TreeCompostBlock> = ModRegistrate.REGISTRATE
        .block("tree_compost") { properties ->
            TreeCompostBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).noOcclusion())
        }
        .blockstate { context, provider ->
            provider.simpleBlock(
                context.entry,
                provider.models().getExistingFile(provider.modLoc("block/tree_compost"))
            )
        }
        .setData(ProviderType.LANG) { _, _ -> }
        .register()

    @JvmField
    val TREE_STUMP: BlockEntry<TreeStumpBlock> = ModRegistrate.REGISTRATE
        .block("tree_stump") { properties ->
            TreeStumpBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion())
        }
        .blockstate { context, provider ->
            provider.simpleBlock(
                context.entry,
                provider.models().getExistingFile(provider.modLoc("block/tree_stump"))
            )
        }
        .setData(ProviderType.LANG) { _, _ -> }
        .register()

    fun init() = Unit
}
