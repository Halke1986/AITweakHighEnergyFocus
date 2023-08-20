package com.genir.hefaitweak

//import com.fs.starfarer.api.GameState
//import com.fs.starfarer.api.Global
//import com.fs.starfarer.api.combat.*
//import com.fs.starfarer.api.input.InputEventAPI
//import org.lazywizard.lazylib.FastTrig
//import org.lazywizard.lazylib.ext.minus
//import org.lazywizard.lazylib.ext.rotate
//import org.lazywizard.lazylib.ui.LazyFont
//import org.lwjgl.util.vector.Vector2f
//import java.awt.Color
//
//// DebugPlugin is used to render debug information during combat.
//// It's disabled by default.
//class DebugPlugin : BaseEveryFrameCombatPlugin() {
//    private var engine: CombatEngineAPI? = null
//
//    private var sum1 = 0
//    private var log = Global.getLogger(this.javaClass)
//    private var font: LazyFont? = null
//    private var drawable: LazyFont.DrawableString? = null
//
//    override fun init(engine: CombatEngineAPI?) {
//        if (Global.getCurrentState() == GameState.TITLE)
//            return
//
//        this.engine = engine
//        this.font = LazyFont.loadFont("graphics/fonts/insignia15LTaa.fnt")
//    }
//
//    override fun advance(amount: Float, events: MutableList<InputEventAPI>?) {
//        super.advance(amount, events)
//        if (engine == null || font == null) return
//
//        sum1 = 0
//
//        val ships = engine!!.ships
//        val ally = ships.firstOrNull { !it.isHostile }
//        val enemy = ships.firstOrNull { it.isHostile }
//
//        if (ally != null && enemy != null) {
////            if (willHitShield(TargetedWeapon(ally.allWeapons[0], enemy)))
////                sum1 = 1f
////            sum1 = willHitShieldArc(ally.location, enemy)
////            sum1 = ally.facing
//        }
//
//        if (ally != null) {
//            sum1 = ally.allWeapons.count { it.isAntiArmor }
//        }
//
//        if (enemy != null) {
////            sum1 = enemy.facing
//        }
//
//
//
//        this.drawable = font!!.createText(sum1.toString(), baseColor = Color.ORANGE)
//    }
//
//    override fun renderInUICoords(viewport: ViewportAPI?) {
//        super.renderInUICoords(viewport)
//
//        drawable?.draw(500f, 500f)
//    }
//}
//
//private fun isRelevantShip(ship: ShipAPI) = when {
//    !ship.isAlive -> false
//    ship.isFighter -> false
//    ship.isDrone -> false
//    ship.system?.id != "highenergyfocus" -> false
//    // Does the ship have energy weapons.
//    !ship.allWeapons.fold(false) { hasEnergy, weapon ->
//        hasEnergy || weapon.type == WeaponAPI.WeaponType.ENERGY
//    } -> false
//
//    else -> true
//}
//
//val ShipAPI.isHostile get() = this.owner == 1
//
//fun willHitShieldArc(l: Vector2f, t: ShipAPI): Float {
//    if (t == null)
//        return 0f
//
////    if (w.target.shield == null || w.target.shield.isOff)
////        return false
//
//    val shield = t.shield
//    val r = (l - shield.location).rotate(-shield.facing)
//    val attackAngle = Math.toDegrees(FastTrig.atan2(r.y.toDouble(), r.x.toDouble())).toFloat()
//    return kotlin.math.abs(attackAngle)
//}


