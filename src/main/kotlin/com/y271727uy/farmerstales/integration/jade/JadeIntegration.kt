package com.y271727uy.farmerstales.integration.jade

import com.y271727uy.farmerstales.FTMod
import com.y271727uy.farmerstales.gameplay.tree.block.TreeStumpBlock
import snownee.jade.api.IWailaPlugin
import snownee.jade.api.IWailaClientRegistration
import snownee.jade.api.WailaPlugin

object JadeIntegration {
    fun init() {
        FTMod.LOGGER.debug("Jade compatibility enabled")
    }
}

@WailaPlugin
class FarmerTalesJadePlugin : IWailaPlugin {
    override fun registerClient(registration: IWailaClientRegistration) {
        registration.registerBlockComponent(TreeStumpTooltipProvider, TreeStumpBlock::class.java)
    }
}
