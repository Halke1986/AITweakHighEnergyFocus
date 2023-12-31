package com.genir.hefaitweak

import com.fs.starfarer.api.GameState
import com.fs.starfarer.api.Global
import com.fs.starfarer.api.combat.*
import org.lazywizard.lazylib.FastTrig
import org.lazywizard.lazylib.combat.AIUtils
import org.lazywizard.lazylib.ext.minus
import org.lazywizard.lazylib.ext.rotate
import org.lwjgl.util.vector.Vector2f

data class TargetedWeapon(val weapon: WeaponAPI, val target: ShipAPI?)

class HighEnergyFocusAI : ShipSystemAIScript {
    private var ship: ShipAPI? = null
    private var engine: CombatEngineAPI? = null

    override fun init(
        ship: ShipAPI?,
        p1: ShipSystemAPI?,
        p2: ShipwideAIFlags?,
        engine: CombatEngineAPI?
    ) {
        this.ship = ship
        this.engine = engine
    }

    override fun advance(p0: Float, p1: Vector2f?, p2: Vector2f?, p3: ShipAPI?) = when {
        engine == null -> Unit
        ship == null -> Unit
        Global.getCurrentState() == GameState.TITLE -> Unit
        engine!!.isPaused -> Unit
        !AIUtils.canUseSystemThisFrame(ship) -> Unit
        !shouldTriggerHEF(ship!!) -> Unit
        else -> ship!!.useSystem()
    }
}

fun shouldTriggerHEF(ship: ShipAPI): Boolean {
    val ew = energyWeapons(shipWeapons(ship))
    val size = largestWeaponSize(ew)
    return ew.any { weaponShouldTriggerHEF(it, size) }
}

fun shipWeapons(ship: ShipAPI): List<TargetedWeapon> =
    ship.weaponGroupsCopy.map { unfoldWeaponGroup(it) }.flatten()

fun unfoldWeaponGroup(wg: WeaponGroupAPI): List<TargetedWeapon> =
    wg.aiPlugins.map { TargetedWeapon(it.weapon, getTarget(wg, it)) }

fun getTarget(wg: WeaponGroupAPI, aiPlugin: AutofireAIPlugin): ShipAPI? =
    if (wg.isAutofiring) aiPlugin.targetShip else wg.ship.shipTarget

fun energyWeapons(weapons: List<TargetedWeapon>): List<TargetedWeapon> =
    weapons.filter { it.weapon.type == WeaponAPI.WeaponType.ENERGY }

fun largestWeaponSize(weapons: List<TargetedWeapon>): WeaponAPI.WeaponSize =
    weapons.fold(WeaponAPI.WeaponSize.SMALL) { size, w ->
        when (w.weapon.size) {
            WeaponAPI.WeaponSize.LARGE -> return WeaponAPI.WeaponSize.LARGE
            WeaponAPI.WeaponSize.MEDIUM -> WeaponAPI.WeaponSize.MEDIUM
            else -> size
        }
    }

fun weaponShouldTriggerHEF(w: TargetedWeapon, size: WeaponAPI.WeaponSize) = when {
    w.weapon.size != size -> false
    !w.weapon.isFiring -> false
    w.weapon.cooldownRemaining != 0f -> false
    w.target == null -> false
    w.target.isFighter -> false
    w.target.isDrone -> false
    w.target.isPhased -> false
    w.weapon.isAntiArmor && willHitShield(w) -> false
    else -> true
}

val WeaponAPI.isAntiArmor
    get() = this.damageType == DamageType.HIGH_EXPLOSIVE ||
            this.hasAIHint(WeaponAPI.AIHints.USE_LESS_VS_SHIELDS)

fun willHitShield(w: TargetedWeapon) = when {
    w.target == null -> false
    w.target.shield == null -> false
    w.target.shield.isOff -> false
    else -> willHitActiveShieldArc(w.weapon, w.target.shield)
}

fun willHitActiveShieldArc(weapon: WeaponAPI, shield: ShieldAPI): Boolean {
    val r = (weapon.location - shield.location).rotate(-shield.facing)
    val attackAngle = Math.toDegrees(FastTrig.atan2(r.y.toDouble(), r.x.toDouble()))
    return kotlin.math.abs(attackAngle) < (shield.activeArc / 2)
}