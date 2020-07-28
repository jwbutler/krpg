package com.jwbutler.krpg.players

import com.jwbutler.krpg.behavior.commands.Command
import com.jwbutler.krpg.behavior.commands.MoveCommand
import com.jwbutler.krpg.behavior.commands.StayCommand
import com.jwbutler.krpg.entities.units.Unit
import com.jwbutler.krpg.geometry.Pixel
import com.jwbutler.krpg.graphics.GameWindow
import com.jwbutler.krpg.utils.getPlayerUnits
import com.jwbutler.krpg.utils.pixelToCoordinates
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

private typealias CommandSupplier = (Unit) -> Command

class MousePlayer : AbstractPlayer()
{
    override val isHuman = true
    private var queuedCommands = mutableMapOf<Unit, CommandSupplier>()

    init
    {
        GameWindow.getInstance().addMouseListener(_getMouseListener())
    }

    override fun chooseCommand(unit: Unit): Command
    {
        return queuedCommands.remove(unit)
            ?.invoke(unit)
            ?: StayCommand(unit)
    }

    private fun _getMouseListener(): MouseListener
    {
        return object : MouseAdapter()
        {
            override fun mouseReleased(e: MouseEvent)
            {
                val pixel = Pixel.fromPoint(e.getPoint())
                val coordinates = pixelToCoordinates(pixel)
                for (u in getPlayerUnits())
                {
                    queuedCommands[u] = { MoveCommand(u, coordinates) }
                }
            }
        }
    }
}