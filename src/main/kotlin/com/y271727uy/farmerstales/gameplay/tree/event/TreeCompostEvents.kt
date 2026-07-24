package com.y271727uy.farmerstales.gameplay.tree.event

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.all.ModBlocks
import net.minecraftforge.event.level.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = FTMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@Suppress("unused")
object TreeCompostEvents {

	@JvmStatic
	@SubscribeEvent
	fun onBlockBreak(event: BlockEvent.BreakEvent) {
		val level = event.level
		if (level.isClientSide) {
			return
		}

		if (level.getBlockState(event.pos).block != ModBlocks.TREE_COMPOST.get()) {
			return
		}

		val abovePos = event.pos.above()
		if (!level.getBlockState(abovePos).isAir) {
			level.destroyBlock(abovePos, false)
		}
	}
}
