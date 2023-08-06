package aitweaks

import com.fs.starfarer.api.GameState
import com.fs.starfarer.api.Global
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin
import com.fs.starfarer.api.combat.CombatEngineAPI
import com.fs.starfarer.api.combat.ViewportAPI
import com.fs.starfarer.api.input.InputEventAPI
import org.lazywizard.lazylib.ui.LazyFont
import java.awt.Color

class Hef : BaseEveryFrameCombatPlugin() {
    private var engine: CombatEngineAPI? = null

    private var log = Global.getLogger(this.javaClass)
    private var font: LazyFont? = null
    private var drawable: LazyFont.DrawableString? = null

    override fun init(engine: CombatEngineAPI?) {
        if (engine == null) {
            return
        }

        // Perform work only during combat, not in title screen.
        if (Global.getCurrentState() == GameState.TITLE) {
            return
        }

        this.engine = engine
        this.font = LazyFont.loadFont("graphics/fonts/insignia15LTaa.fnt")
        this.drawable = font?.createText("TEST", baseColor = Color.GREEN)
    }

    override fun advance(amount: Float, events: MutableList<InputEventAPI>?) {
        if (this.engine == null) {
            return
        }
    }

    override fun renderInUICoords(viewport: ViewportAPI?) {
        super.renderInUICoords(viewport)

        drawable?.draw(500f, 500f)
    }
}

