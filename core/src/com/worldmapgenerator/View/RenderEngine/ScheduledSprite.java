package com.worldmapgenerator.View.RenderEngine;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScheduledSprite extends ScheduledEntity {

    private Sprite sprite;
    private float x, y, scale, rotation;
    private boolean flipX;

    /**
     * Создает спрайт, запланированный на отрисовку.
     *
     * @param sprite - спрайт, который нужно будет отобразить
     * @param x - место отрисовки спрайта по оси X
     * @param y - место отрисовки спрайта по оси Y
     * @param scale - масштабирование спрайта
     * @param rotation - поворот спрайта в градусах
     * @param flipX - при значении true спрайт отражается по горизонтали
     */
    ScheduledSprite(Sprite sprite, float x, float y, float scale, float rotation, boolean flipX) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.rotation = rotation;
        this.flipX = flipX;
    }

    public void draw(SpriteBatch batch, PolygonSpriteBatch polygonBatch, float elapsedTime) {
        sprite.setPosition(x, y);
        sprite.setRotation(rotation);
        sprite.setOrigin(0, 0);
        sprite.setScale(scale);
        sprite.setFlip(flipX, false);
        sprite.draw(batch);
    }

}
