package com.jwbutler.krpg.behavior
import com.jwbutler.krpg.core.GameState
import com.jwbutler.krpg.entities.units.Unit
import com.jwbutler.krpg.geometry.Coordinates

enum class Activity
{
    STANDING,
    WALKING
    {
        override fun onComplete(unit: Unit)
        {
            val x = unit.getCoordinates().x + unit.getDirection().dx
            val y = unit.getCoordinates().y + unit.getDirection().dy
            val coordinates = Coordinates(x, y)
            if (!coordinates.isBlocked())
            {
                unit.moveTo(Coordinates(x, y))
            }
        }
    },
    ATTACKING
    {
        override fun onComplete(unit: Unit)
        {
            val x = unit.getCoordinates().x + unit.getDirection().dx
            val y = unit.getCoordinates().y + unit.getDirection().dy
            val coordinates = Coordinates(x, y)
            val targetUnit = GameState.getInstance().getUnit(coordinates)
            if (targetUnit != null)
            {
                val damage = unit.getDamage(this)
                targetUnit.takeDamage(damage)
            }
        }
    },
    FALLING,
    DEAD;

    override fun toString() = name.toLowerCase()
    open fun onComplete(unit: Unit) {}
}