package com.jwbutler.krpg.utils

import com.jwbutler.rpglib.core.GameState
import com.jwbutler.rpglib.core.GameView
import com.jwbutler.rpglib.entities.Entity
import com.jwbutler.rpglib.geometry.Coordinates
import com.jwbutler.rpglib.geometry.Pixel
import com.jwbutler.rpglib.geometry.Rectangle
import com.jwbutler.rpglib.players.HumanPlayer
import java.awt.Point
import kotlin.math.abs
import kotlin.math.roundToInt

fun getAdjacentCoordinates(coordinates: Coordinates): Set<Coordinates>
{
    val adjacentCoordinates = mutableSetOf<Coordinates>()
    for (dy in -1..1)
    {
        for (dx in -1..1)
        {
            val candidate =
                Coordinates(coordinates.x + dx, coordinates.y + dy)
            if (!candidate.isBlocked())
            {
                adjacentCoordinates.add(candidate)
            }
        }
    }
    val state = GameState.getInstance()
    return adjacentCoordinates
        .filter(state::containsCoordinates)
        .filter { it != coordinates }
        .toSet()
}

fun getAdjacentUnblockedCoordinates(coordinates: Coordinates): Set<Coordinates>
{
    return getAdjacentCoordinates(coordinates).filter { !it.isBlocked() }.toSet()
}

fun manhattanDistance(first: Coordinates, second: Coordinates): Int
{
    return abs(first.x - second.x) + abs(first.y + second.y)
}

fun manhattanDistance(first: Entity, second: Entity): Int
{
    return manhattanDistance(first.getCoordinates(), second.getCoordinates())
}

/**
 * @return the top-left corner of the tile that would be at (x, y)
 */
fun coordinatesToPixel(coordinates: Coordinates): Pixel
{
    val gameView = GameView.getInstance()
    val (tileWidth, tileHeight) = gameView.tileDimensions
    val (gameWidth, gameHeight) = gameView.gameDimensions
    // TODO reference to krpg
    val cameraCoordinates = (GameState.getInstance().getHumanPlayer() as HumanPlayer).getCameraCoordinates()
    val x = (coordinates.x - cameraCoordinates.x) * tileWidth + (gameWidth / 2) - (tileWidth/ 2)
    val y = (coordinates.y - cameraCoordinates.y) * tileHeight + (gameHeight / 2) - (tileHeight / 2)
    return Pixel(x, y)
}

// if camera is at (0, 0):
// (0, 0) -> (-TILE_WIDTH / 2, -TILE_HEIGHT / 2)
fun pixelToCoordinates(pixel: Pixel): Coordinates
{
    val gameView = GameView.getInstance()
    val (tileWidth, tileHeight) = gameView.tileDimensions
    val (gameWidth, gameHeight) = gameView.gameDimensions
    // TODO reference to krpg
    val cameraCoordinates = (GameState.getInstance().getHumanPlayer() as HumanPlayer).getCameraCoordinates()
    val originTopLeft = Pixel(
        (gameWidth / 2) - (cameraCoordinates.x * tileWidth) - (tileWidth / 2),
        (gameHeight / 2) - (cameraCoordinates.y * tileHeight) - (tileHeight / 2)
    )

    val x = (pixel.x - originTopLeft.x) / tileWidth
    val y = (pixel.y - originTopLeft.y) / tileHeight
    return Coordinates(x, y)
}

fun rectFromPixels(first: Pixel, vararg rest: Pixel): Rectangle
{
    val rect = Rectangle(Point(first.x, first.y))
    for (pixel in rest)
    {
        rect.add(pixel.x, pixel.y)
    }
    return rect
}

fun getAverageCoordinates(coordinates: Collection<Coordinates>): Coordinates
{
    val x = coordinates.map(Coordinates::x).average().roundToInt()
    val y = coordinates.map(Coordinates::y).average().roundToInt()
    return Coordinates(x, y)
}