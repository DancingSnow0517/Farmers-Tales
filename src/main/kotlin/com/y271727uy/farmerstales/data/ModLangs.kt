package com.y271727uy.farmerstales.data

import com.tterrag.registrate.providers.RegistrateLangProvider
import com.y271727uy.farmerstales.FTMod

object ModLangs {
    fun init(provider: RegistrateLangProvider) {
        provider.add("itemGroup.${FTMod.MODID}", "Farmer's Tales")
    }
}
