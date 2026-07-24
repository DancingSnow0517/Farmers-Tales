package com.y271727uy.farmerstales.gameplay.tree.event

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.gameplay.tree.TreeDefinitions
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = FTMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
@Suppress("unused")
object TreeSeedTooltipEvents {
	@JvmStatic
	@SubscribeEvent
	fun onItemTooltip(event: ItemTooltipEvent) {
		if (!TreeDefinitions.isWhiteListedSeed(event.itemStack)) {
			return
		}

		event.toolTip.add(Component.translatable("tooltip.farmerstales.tree_seed.cultivable").withStyle(ChatFormatting.GREEN))
	}
}
