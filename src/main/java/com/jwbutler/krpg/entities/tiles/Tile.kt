package com.jwbutler.krpg.entities.tiles

import com.jwbutler.krpg.core.GameState
import com.jwbutler.krpg.entities.Entity
import com.jwbutler.krpg.geometry.Coordinates
import com.jwbutler.krpg.graphics.GameRenderer
import com.jwbutler.krpg.graphics.RenderLayer
import com.jwbutler.krpg.graphics.images.ImageLoader
import com.jwbutler.krpg.graphics.Renderable
import com.jwbutler.krpg.graphics.sprites.StaticSprite

class Tile(type: TileType, private val coordinates: Coordinates) : Entity
{
    override val sprite = StaticSprite(
        _getImage(type),
        RenderLayer.FLOOR_TILE
    )

    override fun isBlocking() = false
    // TODO: create GameState#getCoordinates(Tile)?
    override fun getCoordinates() = coordinates
    override fun exists() = GameState.getInstance().containsEntity(this)
    override fun update() {}
    override fun afterRender() {}

    override fun render(): Renderable = sprite.render(this)
}

private fun _getImage(type: TileType) = ImageLoader.getInstance()
    .loadImage("tiles/${type.filename}")