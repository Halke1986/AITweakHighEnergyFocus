package aitweaks

import com.fs.starfarer.api.GameState
import com.fs.starfarer.api.Global
import com.fs.starfarer.api.combat.*
import com.fs.starfarer.api.input.InputEventAPI
import org.lazywizard.lazylib.ui.LazyFont
import java.awt.Color

class Hef : BaseEveryFrameCombatPlugin() {
    private var engine: CombatEngineAPI? = null

    private var sum1 = 0
    private var log = Global.getLogger(this.javaClass)
    private var font: LazyFont? = null
    private var drawable: LazyFont.DrawableString? = null

    override fun init(engine: CombatEngineAPI?) {
        if (engine == null) {
            return
        }

        // Perform work only during combat, not in the title screen.
        if (Global.getCurrentState() == GameState.TITLE) {
            return
        }

        this.engine = engine

        // Debugging font.
        this.font = LazyFont.loadFont("graphics/fonts/insignia15LTaa.fnt")
    }

    override fun advance(amount: Float, events: MutableList<InputEventAPI>?) {
        super.advance(amount, events)
        if (this.engine == null) {
            return
        }

        val ships = engine!!.ships.filter { isRelevantShip(it) }
        val firingCount = ships.fold(0) { sum, it ->
            mainWeapons(it).filter {
                it.isFiring && it.cooldownRemaining == 0f
            }.size + sum
        }

        sum1 += firingCount

        val text = sum1.toString()
        this.drawable = font?.createText(text, baseColor = Color.ORANGE)
    }

    override fun renderInUICoords(viewport: ViewportAPI?) {
        super.renderInUICoords(viewport)

        drawable?.draw(500f, 500f)
    }

    private fun isRelevantShip(ship: ShipAPI) = when {
        !ship.isAlive -> false
        ship.isFighter -> false
        ship.isDrone -> false
        ship.system?.id != "highenergyfocus" -> false
        // Does the ship have energy weapons.
        !ship.allWeapons.fold(false) { hasEnergy, weapon ->
            hasEnergy || weapon.type == WeaponAPI.WeaponType.ENERGY
        } -> false

        else -> true
    }

//    private fun isRelevantShip(ship: ShipAPI): Boolean {
//        if (!ship.isAlive || ship.isFighter || ship.isDrone) {
//            return false
//        }
//
//        if (ship.system?.id != "highenergyfocus") {
//            return false
//        }
//
//        if (!ship.allWeapons.fold(false) { isEnergy, weapon ->
//            isEnergy || weapon.type == WeaponAPI.WeaponType.ENERGY
//        })
//
////        if (ship.system.isActive || ship.system.isOutOfAmmo) {
////            return false
////        }
//
//
//        // Don't attempt to control the player piloted ship.
//        // NOTE: `isUIAutopilotOn` is `true` when autopilot is disabled.
////        if (ship == engine!!.playerShip && engine!!.isUIAutopilotOn) {
////            return false
////        }
//
//        return true
//    }

//    private fun mainWeapons(ship: ShipAPI): Int {

    // mainWeapons return list of ships largest energy weapons.
    private fun mainWeapons(ship: ShipAPI): List<WeaponAPI> {
        val energy = ship.allWeapons.filter { it.type == WeaponAPI.WeaponType.ENERGY }
        val large = energy.filter { it.size == WeaponAPI.WeaponSize.LARGE }
        val medium = energy.filter { it.size == WeaponAPI.WeaponSize.MEDIUM }

        return when {
            large.isNotEmpty() -> large
            medium.isNotEmpty() -> medium
            else -> energy.filter { it.size == WeaponAPI.WeaponSize.SMALL }
        }
    }
}
