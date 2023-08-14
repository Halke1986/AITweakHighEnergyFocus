package com.genir.hefaitweak

import com.fs.starfarer.api.combat.*
import org.lazywizard.lazylib.combat.AIUtils
import org.lwjgl.util.vector.Vector2f

class HighEnergyFocusAI : ShipSystemAIScript {
    private var ship: ShipAPI? = null
    private var engine: CombatEngineAPI? = null

    override fun init(
        ship: ShipAPI?,
        system: ShipSystemAPI?,
        flags: ShipwideAIFlags?,
        engine: CombatEngineAPI?
    ) {
        this.ship = ship
        this.engine = engine
    }

    override fun advance(
        p0: Float,
        p1: Vector2f?,
        p2: Vector2f?,
        p3: ShipAPI?
    ) {
        if (engine!!.isPaused) return
        if (!AIUtils.canUseSystemThisFrame(ship)) return

        if (mainWeapons().fold(false)
            { isFiring, w -> isFiring || (w.isFiring && w.cooldownRemaining == 0f) }
        ) {
            ship!!.useSystem()
        }
    }

    // mainWeapons return list of ships largest energy weapons.
    private fun mainWeapons(): List<WeaponAPI> {
        val energy = ship!!.allWeapons.filter { it.type == WeaponAPI.WeaponType.ENERGY }

        val large = energy.filter { it.size == WeaponAPI.WeaponSize.LARGE }
        if (large.isNotEmpty()) return large

        val medium = energy.filter { it.size == WeaponAPI.WeaponSize.MEDIUM }
        if (medium.isNotEmpty()) return medium

        return energy.filter { it.size == WeaponAPI.WeaponSize.SMALL }
    }
}
