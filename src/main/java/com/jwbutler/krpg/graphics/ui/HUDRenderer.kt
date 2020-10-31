package com.jwbutler.krpg.graphics.ui

import com.jwbutler.krpg.utils.getPlayerUnits
import com.jwbutler.rpglib.core.GameView
import com.jwbutler.rpglib.geometry.Pixel
import com.jwbutler.rpglib.graphics.images.Colors
import com.jwbutler.rpglib.graphics.images.Image

class HUDRenderer
{
    companion object
    {
        const val HEIGHT = 40 // If HEIGHT = 180, then this is ~22%
    }

    private val image: Image

    init
    {
        val (width, height) = GameView.getInstance().gameDimensions
        image = Image.create(width, height)
    }

    fun render(): Pair<Image, Pixel>
    {
        _renderBackground()

        var x = 5
        for (unit in getPlayerUnits())
        {
            val card = UnitCard(unit)
            val cardImage = card.render()
            image.drawImage(cardImage, x, 5)
            x += UnitCard.WIDTH + 5
        }

        val (_, gameHeight) = GameView.getInstance().gameDimensions
        return Pair(image, Pixel(0, gameHeight - HEIGHT))
    }

    private fun _renderBackground()
    {
        val (gameWidth, _) = GameView.getInstance().gameDimensions
        image.fillRect(0, 0, gameWidth - 1, HEIGHT - 1, Colors.BLACK)
        image.drawRect(0, 0, gameWidth - 1, HEIGHT - 1, Colors.WHITE)
    }
}
