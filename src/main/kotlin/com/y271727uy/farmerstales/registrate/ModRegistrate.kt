package com.y271727uy.farmerstales.registrate

import com.tterrag.registrate.Registrate
import com.y271727uy.farmerstales.FTMod

object ModRegistrate {
    @JvmField
    val REGISTRATE: Registrate = Registrate.create(FTMod.Companion.MODID)
}