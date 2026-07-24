package com.y271727uy.farmerstales.integration.jade

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.gameplay.tree.block.entity.TreeStumpBlockEntity
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import snownee.jade.api.BlockAccessor
import snownee.jade.api.IBlockComponentProvider
import snownee.jade.api.ITooltip
import snownee.jade.api.config.IPluginConfig

object TreeStumpTooltipProvider : IBlockComponentProvider {
    private val UID = ResourceLocation.fromNamespaceAndPath(FTMod.MODID, "tree_stump_jade")

    override fun getUid(): ResourceLocation = UID

    override fun appendTooltip(tooltip: ITooltip, accessor: BlockAccessor, config: IPluginConfig) {
        val stump = accessor.blockEntity as? TreeStumpBlockEntity ?: return
        if (!stump.validateAndNormalize()) {
            return
        }

        val score = stump.maintenanceScore()
        val bonus = stump.growthSpeedBonus()
        tooltip.add(Component.translatable("tooltip.farmerstales.jade.tree_stump.title").withStyle(ChatFormatting.GOLD))
        tooltip.add(Component.translatable("tooltip.farmerstales.jade.tree_stump.fertility", stump.fertilize).withStyle(ChatFormatting.GREEN))
        tooltip.add(Component.translatable("tooltip.farmerstales.jade.tree_stump.water", stump.water).withStyle(ChatFormatting.AQUA))
        tooltip.add(Component.translatable("tooltip.farmerstales.jade.tree_stump.branches", stump.branches).withStyle(ChatFormatting.YELLOW))
        tooltip.add(Component.translatable("tooltip.farmerstales.jade.tree_stump.maintenance_score", score).withStyle(ChatFormatting.GRAY))

        when {
            stump.isGrowthPaused() -> tooltip.add(Component.translatable("tooltip.farmerstales.jade.tree_stump.maintenance_paused").withStyle(ChatFormatting.RED))
            stump.isWellMaintained() -> tooltip.add(Component.translatable("tooltip.farmerstales.jade.tree_stump.maintenance_bonus", bonus).withStyle(ChatFormatting.GREEN))
            else -> tooltip.add(Component.translatable("tooltip.farmerstales.jade.tree_stump.maintenance_active").withStyle(ChatFormatting.YELLOW))
        }
    }
}
