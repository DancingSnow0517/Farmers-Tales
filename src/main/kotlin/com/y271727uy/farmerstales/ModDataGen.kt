package com.y271727uy.farmerstales

import com.tterrag.registrate.providers.ProviderType

object ModDataGen {
    fun init() {
        ModRegistrate.REGISTRATE.addDataGenerator(ProviderType.LANG) { provider ->
            provider.add("itemGroup.${FTMod.MODID}", "Farmer's Tales")
        }
    }
}
