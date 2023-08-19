package com.genir.hefaitweak

import com.fs.starfarer.api.GameState
import com.fs.starfarer.api.Global
import com.fs.starfarer.api.combat.*
import com.fs.starfarer.api.input.InputEventAPI
import org.lazywizard.lazylib.ui.LazyFont
import java.awt.Color

// DebugPlugin is used to render debug information during combat.
// It's disabled by default.
class DebugPlugin : BaseEveryFrameCombatPlugin() {
    private var engine: CombatEngineAPI? = null

    private var sum1 = 0
    private var log = Global.getLogger(this.javaClass)
    private var font: LazyFont? = null
    private var drawable: LazyFont.DrawableString? = null

    override fun init(engine: CombatEngineAPI?) {
        if (Global.getCurrentState() == GameState.TITLE)
            return

        this.engine = engine
        this.font = LazyFont.loadFont("graphics/fonts/insignia15LTaa.fnt")
    }

    override fun advance(amount: Float, events: MutableList<InputEventAPI>?) {
        super.advance(amount, events)
        if (engine == null || font == null) return

        sum1 = engine!!.ships.count { it.isAlive && it.isStationModule }

        this.drawable = font!!.createText(sum1.toString(), baseColor = Color.ORANGE)
    }

    override fun renderInUICoords(viewport: ViewportAPI?) {
        super.renderInUICoords(viewport)

        drawable?.draw(500f, 500f)
    }
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


