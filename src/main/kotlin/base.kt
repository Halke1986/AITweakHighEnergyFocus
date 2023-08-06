package aitweaks

import com.fs.starfarer.api.BaseModPlugin

class AITweaks : BaseModPlugin() {


    override fun onGameLoad(new: Boolean) {
        super.onGameLoad(new)
        throw Exception("Test")
    }
}