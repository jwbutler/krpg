package com.jwbutler.krpg.entities.units

import com.jwbutler.krpg.geometry.Coordinates
import com.jwbutler.krpg.graphics.PaletteSwaps
import com.jwbutler.krpg.graphics.sprites.WizardSprite
import com.jwbutler.krpg.players.Player

private fun _getSprite() = WizardSprite(PaletteSwaps.WHITE_TRANSPARENT)

abstract class WizardUnit(player: Player, coordinates: Coordinates, hp: Int) : AbstractUnit(player, _getSprite(), coordinates, hp)